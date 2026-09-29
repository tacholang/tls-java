package net.strokkur.tls.model.environment;

import net.strokkur.tls.model.environment.impl.TlsDeclarationImpl;

import java.util.List;

public interface TlsDeclaration {

  static TlsDeclaration declaration(String name, List<TlsParameter> parameters, List<TlsDeclarationFlag> flags) {
    return new TlsDeclarationImpl(name, List.copyOf(parameters), List.copyOf(flags));
  }

  String name();

  List<TlsParameter> parameters();

  List<TlsDeclarationFlag> flags();
}
