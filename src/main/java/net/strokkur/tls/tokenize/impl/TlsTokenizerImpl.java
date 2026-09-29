package net.strokkur.tls.tokenize.impl;

import net.strokkur.tls.tokenize.TlsToken;
import net.strokkur.tls.tokenize.TlsTokenType;
import net.strokkur.tls.tokenize.TlsTokenizer;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TlsTokenizerImpl implements TlsTokenizer {

  @Override
  public List<TlsToken> tokenize(Reader reader) {
    try {
      final TokenizeContext context = new TokenizeContext(reader);
      final List<TlsToken> out = new ArrayList<>();

      while (context.hasNext()) {
        advanceWhitespace(context);
        out.add(nextToken(context));
      }

      return Collections.unmodifiableList(out);
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
  }

  private TlsToken nextToken(TokenizeContext ctx) throws IOException {
    return switch (ctx.current) {
      case '=' -> new TlsToken("=", ctx.lineIndex, ctx.lineOffset, TlsTokenType.EQUALS);
      case '\n' -> new TlsToken("\n", ctx.lineIndex, ctx.lineOffset, TlsTokenType.NEWLINE);
      case '{' -> new TlsToken("{", ctx.lineIndex, ctx.lineOffset, TlsTokenType.CURLY_BRACE_OPEN);
      case '}' -> new TlsToken("}", ctx.lineIndex, ctx.lineOffset, TlsTokenType.CURLY_BRACE_CLOSE);
      case -1 -> new TlsToken("", ctx.lineIndex, ctx.lineOffset, TlsTokenType.EOF);

      case '"' -> {
        // This is the start of a string.
        ctx.startRecorder();
        do {
          ctx.advance();
        } while (ctx.hasCurrent() && ctx.current != '"');

        //noinspection StatementWithEmptyBody
        if (!ctx.hasCurrent()) {
          // We reached the EOF before the string finished parsing.
          // For now, this means nothing.
        } else {
          ctx.advance(); // --> curr = <after-string>
        }

        yield ctx.stopRecorderIntoToken(TlsTokenType.STRING);
      }

      default -> {
        // If it starts with a number, it is a number. Identifiers may not start with numbers, after all.
        if (isNumber(ctx.current)) {
          ctx.startRecorder();
          ctx.advance();
          if (ctx.current == 'x') {
            // Hexadecimal number.
            ctx.advance();
          }
          while (isNumber(ctx.current)) {
            ctx.advance();
          }
          yield ctx.stopRecorderIntoToken(TlsTokenType.STRING);
        }

        // It must be something identifier-like!
        if (isAlpha(ctx.current) || ctx.current == '_') {
          ctx.startRecorder();
          do {
            ctx.advance();
          } while (isIdentifierBody(ctx.current));
          yield ctx.stopRecorderIntoToken(TlsTokenType.IDENTIFIER);
        }

        // No idea what it is. Let's collect characters until we hit whitespace.
        ctx.startRecorder();
        while (!isWhitespace(ctx.current)) {
          ctx.advance();
        }
        yield ctx.stopRecorderIntoToken(TlsTokenType.UNRECOGNIZED);
      }
    };
  }

  private void advanceWhitespace(TokenizeContext ctx) throws IOException {
    while (isWhitespace(ctx.current)) {
      ctx.advance();
    }
    advanceComments(ctx);
  }

  private void advanceComments(TokenizeContext ctx) throws IOException {
    if (ctx.current == '/' && ctx.next == '/') {
      // Comment until end of line
      do {
        ctx.advance();
      } while (ctx.current != '\n');
      ctx.advance();
      advanceWhitespace(ctx);
      return;
    }

    if (ctx.current == '/' && ctx.next == '*') {
      // Multiline comment
      do {
        ctx.advance();
      } while (ctx.current != '*' && ctx.next != '/');
      ctx.advance(); // --> curr == '/'
      ctx.advance(); // --> curr == <after-comment>
      advanceWhitespace(ctx);
    }
  }

  private static boolean isAlpha(int codepoint) {
    return (codepoint >= 'a' && codepoint <= 'z') || (codepoint >= 'A' && codepoint <= 'Z');
  }

  private static boolean isNumber(int codepoint) {
    return codepoint >= '0' && codepoint <= '9';
  }

  private static boolean isWhitespace(int codepoint) {
    return codepoint == ' ' || codepoint == '\t' || codepoint == '\n' || codepoint == '\r';
  }

  private static boolean isIdentifierBody(int codepoint) {
    return isAlpha(codepoint) || isNumber(codepoint) || codepoint == '_' || codepoint == '-' || codepoint == '$' || codepoint == '.';
  }

  private static final class TokenizeContext {
    final Reader reader;
    int previous;
    int current;
    int next;

    int lineIndex = 0;
    int lineOffset = 0;

    StringBuilder stringBuilder = null;

    TokenizeContext(Reader reader) throws IOException {
      this.reader = reader;
      previous = -1;
      current = reader.read();
      next = reader.read();
    }

    void advance() throws IOException {
      previous = current;
      current = next;
      next = reader.read();

      if (previous == '\n') {
        lineIndex++;
        lineOffset = 0;
      } else {
        lineOffset++;
      }

      if (stringBuilder != null) {
        stringBuilder.append((char) previous);
      }
    }

    void startRecorder() {
      stringBuilder = new StringBuilder();
    }

    String stopRecorder() {
      final String out = stringBuilder.toString();
      stringBuilder = null;
      return out;
    }

    TlsToken stopRecorderIntoToken(TlsTokenType tokenType) {
      return new TlsToken(stopRecorder(), lineIndex, lineOffset, tokenType);
    }

    boolean hasPrevious() {
      return previous != -1;
    }

    boolean hasCurrent() {
      return current != -1;
    }

    boolean hasNext() {
      return next != -1;
    }
  }
}
