package net.strokkur.tls.model.environment.impl;

import net.strokkur.tls.model.environment.TlsDeclarationScoped;
import net.strokkur.tls.model.environment.TlsParameter;
import net.strokkur.tls.model.environment.TlsScope;

import java.util.List;

public record TlsDeclarationScopedImpl(
  String name,
  List<TlsParameter> parameters,
  TlsScope scoped
) implements TlsDeclarationScoped {
}
