package net.strokkur.tls.model.environment;

import net.strokkur.tls.model.environment.impl.TlsScopeImpl;

import java.util.List;

public interface TlsScope {

  static TlsScope.Builder scopeBuilder() {
    return new TlsScopeImpl.BuilderImpl();
  }

  List<TlsDeclaration> declarations();

  List<TlsDeclarationScoped> declarationsScoped();

  List<TlsVariable> variables();

  List<TlsMethod> methods();

  interface Builder {

    Builder define(TlsDeclaration declaration);

    Builder define(TlsDeclarationScoped declarationScoped);

    Builder define(TlsVariable variable);

    Builder define(TlsMethod method);

    TlsScope toScope();
  }
}
