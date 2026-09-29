package net.strokkur.tls.tokenize;

import net.strokkur.tls.tokenize.impl.TlsTokenizerImpl;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public interface TlsTokenizer {

  TlsTokenizer TOKENIZER = new TlsTokenizerImpl();

  List<TlsToken> tokenize(Reader reader);

  default List<TlsToken> tokenize(CharSequence string) {
    return tokenize(Reader.of(string));
  }

  default List<TlsToken> tokenize(Path path) throws IOException {
    return tokenize(Files.newBufferedReader(path));
  }
}
