package net.strokkur.tls.model.type;

import java.util.Set;

public interface TlsLiteral extends TlsType {
  Set<String> literals();
}
