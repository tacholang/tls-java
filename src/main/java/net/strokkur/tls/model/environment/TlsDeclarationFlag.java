package net.strokkur.tls.model.environment;

import net.strokkur.tls.model.environment.impl.TlsDeclarationFlagImpl;

import java.util.List;

public interface TlsDeclarationFlag {

  static TlsDeclarationFlag declarationFlag(String name, List<TlsParameter> parameters) {
    return new TlsDeclarationFlagImpl(name, List.copyOf(parameters));
  }

  String name();

  List<TlsParameter> parameters();
}
