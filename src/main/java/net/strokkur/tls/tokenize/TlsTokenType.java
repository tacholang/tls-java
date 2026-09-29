package net.strokkur.tls.tokenize;

public enum TlsTokenType {
  IDENTIFIER,
  STRING,
  INTEGER,

  CURLY_BRACE_OPEN,
  CURLY_BRACE_CLOSE,
  EQUALS,

  NEWLINE,
  EOF,
  UNRECOGNIZED,
}
