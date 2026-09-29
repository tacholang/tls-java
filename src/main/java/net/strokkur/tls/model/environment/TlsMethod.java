package net.strokkur.tls.model.environment;

import net.strokkur.tls.model.environment.impl.TlsMethodImpl;
import net.strokkur.tls.model.type.TlsType;
import net.strokkur.tls.model.valued.TlsParameterValue;
import net.strokkur.tls.model.valued.TlsValue;

import java.util.List;
import java.util.function.BiFunction;

public interface TlsMethod {

  static TlsMethod method(
    String name,
    TlsType type,
    List<TlsParameter> parameters,
    BiFunction<TlsMethod, List<TlsParameterValue>, TlsValue> valueFunction
  ) {
    return new TlsMethodImpl(name, type, List.copyOf(parameters), valueFunction);
  }

  String name();

  TlsType type();

  List<TlsParameter> parameters();

  TlsValue value(List<TlsParameterValue> values);
}
