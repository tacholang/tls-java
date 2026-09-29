package net.strokkur.tls.model.environment;

import net.strokkur.tls.model.environment.impl.TlsDeclarationScopedImpl;

import java.util.List;

public interface TlsDeclarationScoped {

  static TlsDeclarationScoped declarationScoped(String name, List<TlsParameter> parameters, TlsScope scoped) {
    return new TlsDeclarationScopedImpl(name, List.copyOf(parameters), scoped);
  }

  String name();

  List<TlsParameter> parameters();

  TlsScope scoped();
}
