package net.strokkur.tls.test.tokenize;

import net.strokkur.tls.tokenize.TlsToken;
import net.strokkur.tls.tokenize.TlsTokenType;
import net.strokkur.tls.tokenize.TlsTokenizer;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class TestTokenizer {

  @Test
  void testSimpleTokenization() {
    final List<TlsToken> tokens = TlsTokenizer.TOKENIZER.tokenize("""
      build {
        name "${project_name}-${architecture}"
        version "${project_version}+${git_ref_short}"
      }
      
      // Overloaded scoped declaration with a literal parameter
      build x86 {
        name project_name
        version "${project_version}+${git_ref_short}"
      }""");

    final TokenChecker checker = new TokenChecker(tokens);
    checker.expectIdentifier("build");
    checker.expect("{", TlsTokenType.CURLY_BRACE_OPEN);
    checker.expectIdentifier("name");
    checker.expectString("${project_name}-${architecture}");
    checker.expectIdentifier("version");
    checker.expectString("${project_version}+${git_ref_short}");
    checker.expect("}", TlsTokenType.CURLY_BRACE_CLOSE);
    checker.expectIdentifier("build");
    checker.expectIdentifier("x86");
    checker.expect("{", TlsTokenType.CURLY_BRACE_OPEN);
    checker.expectIdentifier("name");
    checker.expectIdentifier("project_name");
    checker.expectIdentifier("version");
    checker.expectString("${project_version}+${git_ref_short}");
    checker.expect("}", TlsTokenType.CURLY_BRACE_CLOSE);
    checker.expectEof();
  }

  private record TokenChecker(Deque<TlsToken> tokens) {
    public TokenChecker(List<TlsToken> tokens) {
      this(new ArrayDeque<>(tokens));
    }

    private void expectToken(String literal, TlsTokenType type) {
      final TlsToken token = tokens.pop();
      assertEquals(
        "Token literal: `%s`. Token type: `%s`".formatted(literal, type),
        "Token literal: `%s`. Token type: `%s`".formatted(token.literal(), token.type())
      );
    }

    public void expect(String literal, TlsTokenType type) {
      if (tokens.isEmpty()) {
        fail("Expected another token [%s/%s], but found none.".formatted(literal, type));
      }
      expectToken(literal, type);
    }

    public void expectIdentifier(String identifier) {
      expect(identifier, TlsTokenType.IDENTIFIER);
    }

    public void expectString(String content) {
      expect('"' + content + '"', TlsTokenType.STRING);
    }

    public void expectEof() {
      if (tokens.isEmpty()) {
        fail("Expected EOF, but found none.");
      }

      expectToken("", TlsTokenType.EOF);
      if (!tokens.isEmpty()) {
        fail("Expected no more tokens, but found %d more.".formatted(tokens.size()));
      }
    }
  }
}
