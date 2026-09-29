package net.strokkur.tls.model.environment.impl;

import net.strokkur.tls.model.environment.TlsDeclarationFlag;
import net.strokkur.tls.model.environment.TlsParameter;

import java.util.List;

public record TlsDeclarationFlagImpl(
  String name,
  List<TlsParameter> parameters
) implements TlsDeclarationFlag {
}
