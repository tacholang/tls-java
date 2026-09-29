package net.strokkur.tls.model.environment.impl;

import net.strokkur.tls.model.environment.TlsDeclaration;
import net.strokkur.tls.model.environment.TlsDeclarationScoped;
import net.strokkur.tls.model.environment.TlsMethod;
import net.strokkur.tls.model.environment.TlsScope;
import net.strokkur.tls.model.environment.TlsVariable;

import java.util.ArrayList;
import java.util.List;

public record TlsScopeImpl(
  List<TlsDeclaration> declarations,
  List<TlsDeclarationScoped> declarationsScoped,
  List<TlsVariable> variables,
  List<TlsMethod> methods
) implements TlsScope {

  public static class BuilderImpl implements TlsScope.Builder {
    private final List<TlsDeclaration> declarations = new ArrayList<>();
    private final List<TlsDeclarationScoped> declarationsScoped = new ArrayList<>();
    private final List<TlsVariable> variables = new ArrayList<>();
    private final List<TlsMethod> methods = new ArrayList<>();

    @Override
    public BuilderImpl define(TlsDeclaration declaration) {
      declarations.add(declaration);
      return this;
    }

    @Override
    public BuilderImpl define(TlsDeclarationScoped declarationScoped) {
      declarationsScoped.add(declarationScoped);
      return this;
    }

    @Override
    public BuilderImpl define(TlsVariable variable) {
      variables.add(variable);
      return this;
    }

    @Override
    public BuilderImpl define(TlsMethod method) {
      methods.add(method);
      return this;
    }

    @Override
    public TlsScopeImpl toScope() {
      return new TlsScopeImpl(
        List.copyOf(declarations),
        List.copyOf(declarationsScoped),
        List.copyOf(variables),
        List.copyOf(methods)
      );
    }
  }
}
