package net.strokkur.tls.tokenize;

public record TlsToken(
  String literal,
  int line,
  int offsetInLine,
  TlsTokenType type
) {
}
