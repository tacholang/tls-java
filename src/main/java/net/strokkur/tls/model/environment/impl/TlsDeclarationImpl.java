package net.strokkur.tls.model.environment.impl;

import net.strokkur.tls.model.environment.TlsDeclaration;
import net.strokkur.tls.model.environment.TlsDeclarationFlag;
import net.strokkur.tls.model.environment.TlsParameter;

import java.util.List;

public record TlsDeclarationImpl(
  String name,
  List<TlsParameter> parameters,
  List<TlsDeclarationFlag> flags
) implements TlsDeclaration {
}
