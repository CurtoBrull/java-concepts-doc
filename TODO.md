# Java Concepts - Pendientes

## Java Core
- [x] Explicar diferencia entre paso por valor y paso por referencia en Java

## General
- [x] Añadir favicon

## Contenido - Mejoras y Conceptos Faltantes para Entrevistas

### 🔴 FALTAN (preguntas frecuentes en entrevistas)
- [x] **String vs StringBuilder vs StringBuffer** - inmutabilidad, rendimiento, sincronización (IMPLEMENTADO)
- [x] **equals() y hashCode()** - contrato, importancia en HashMap/HashSet, cómo implementarlos correctamente (IMPLEMENTADO)
- [x] **Optional\<T\>** - evitar NullPointerException, orElse vs orElseGet, ifPresent (IMPLEMENTADO)
- [x] **Virtual Threads** - Project Loom, lightweight threads, blocking, pinning, structured concurrency (IMPLEMENTADO)
- [x] **Sealed Classes (Java 17)** - restringir herencia, permits, useful para switch expressions (IMPLEMENTADO)
- [x] **Pattern Matching for switch (Java 21)** - expresiones de switch mejoradas (IMPLEMENTADO)
- [ ] **Record** - ampliar explicación (cláusula canónica, métodos personalizados, immutable)
- [x] **BigDecimal** - precisión decimal, compareTo vs equals, rounding modes (IMPLEMENTADO)
- [x] **Local-Variable Type Inference (var)** - Java 10+, limitaciones, buenas prácticas (IMPLEMENTADO)
- [x] **Functional Interfaces** - Supplier, Consumer, Function, Predicate, sus métodos (IMPLEMENTADO)

### 🟡 AMPLIAR (explicaciones superficiales)
- [x] **Clone** - shallow copy vs deep copy, CopyConstructor, CloneNotSupportedException (IMPLEMENTADO)
- [ ] **Anotaciones** - @Override, @Deprecated, @FunctionalInterface, @SuppressWarnings, @SafeVarargs
- [ ] **Streams** - collect, partitionBy, groupingBy, flatMap, reduce en profundidad
- [ ] **Parallel Streams** - ForkJoinPool, orden vs paralelismo, cuando NO usarlos
- [ ] **Reflection** - Class.forName(), getDeclaredMethods(), uso en frameworks (Spring, JUnit)
- [ ] **Garbage Collectors** - G1 vs ZGC vs Shenandoah vs Serial, cómo elegir, flags
- [ ] **JVM Memory Model** - heap/stack/metaspace, generational garbage collection
- [x] **DateTime API (Java 8+)** - LocalDateTime, ZonedDateTime, Instant, Duration, Period, ZoneId (IMPLEMENTADO)

### 🟢 MEJORAR CONTENIDO EXISTENTE
- [ ] **equals y hashCode** (concepto "Objeto") - ampliar con código de implementación correcta
- [ ] **Enums y Records** - crear concepto separado para Record con más profundidad
- [ ] **Clases Internas** - mencionar también lambda scope y effectively final
- [ ] **interfaces** - ampliar con @FunctionalInterface y ejemplos de lambda

---

## Evolución de Java por Versión (entrevistas)

### Java 8 (2014) - REVOLUCIÓN
- [ ] **Lambda expressions** - expresiones lambda, sintaxis `(a, b) -> a + b`
- [ ] **Method references** - `String::toUpperCase`, `List::size`
- [ ] **Stream API** - `stream()`, `filter()`, `map()`, `collect()`, pipeline de operaciones
- [ ] **Optional\<T\>** - alternativa a null, `ifPresent()`, `orElse()`, `orElseGet()`
- [ ] **Interface default methods** - métodos con implementación en interfaces
- [ ] **Interface static methods** - métodos estáticos en interfaces
- [ ] **java.time API** - `LocalDate`, `LocalDateTime`, `ZonedDateTime`, `Instant`, `Duration`
- [ ] **Nashorn JavaScript engine** - motor JS (deprecado después)
- [ ] **ConcurrentHashMap** - nuevos métodos `forEach`, `compute`, `merge`
- [ ] **Base64 encoding/decoding** - `Base64.getEncoder()`
- [ ] **@FunctionalInterface** - anotación para interfaces funcionales

### Java 9 (2017)
- [ ] **Module System (Project Jigsaw)** - `module-info.java`, requires, exports
- [ ] **JShell** - REPL interactivo para Java
- [ ] **Factory methods for collections** - `List.of()`, `Set.of()`, `Map.of()`
- [ ] **Private interface methods** - métodos privados en interfaces
- [ ] **Stream improvements** - `takeWhile()`, `dropWhile()`, `iterate()` con condición
- [ ] **Optional improvements** - `ifPresentOrElse()`, `or()` method
- [ ] **HTTP/2 Client** - API HTTP moderna (en desuso después por WebClient)
- [ ] **Process API improvements** - control de procesos nativa
- [ ] **Stack-Walking API** - recorrer stack traces eficientemente
- [ ] **Deprecated APIs** - `@Deprecated` con `since` y `forRemoval`

### Java 10 (2018)
- [ ] **Local-Variable Type Inference** - `var` para variables locales
- [ ] **Application Class-Data Sharing** - AppCDS para reducir startup
- [ ] **Graal VM como JIT** - integración Graal
- [ ] **Parallel Full GC for G1** - mejora rendimiento G1
- [ ] **Thread-Local Handshakes** - manipulación de threads individual

### Java 11 (2018) - LTS
- [ ] **HTTP Client (Standard)** - API HTTP正式 (reemplaza el de Java 9)
- [ ] **Running .java files directly** - `java HelloWorld.java`
- [ ] **String methods** - `isBlank()`, `lines()`, `strip()`, `repeat()`
- [ ] **Files methods** - `readString()`, `writeString()`
- [ ] **Collection toArray** - `List.toArray(IntFunction)`
- [ ] **Predicate.not()** - negación de predicates
- [ ] **ZGC** - Garbage Collector de baja latencia (experimental)
- [ ] **Removed Java EE modules** - JAXB, JavaFX eliminados

### Java 12 (2019)
- [ ] **Switch Expressions (Preview)** - expresiones switch, `yield` keyword
- [ ] **File mismatch()** - comparar archivos eficientemente
- [ ] **G1 GC improvements** - `NUMA-aware` memory allocation, prompt return unused memory
- [ ] **Shenandoah GC** - GC de baja latencia (experimental)

### Java 13 (2019)
- [ ] **Switch Expressions (Standard)** - expressions como estado
- [ ] **Text Blocks (Preview)** - `"""..."""` strings multilínea
- [ ] **Socket API Reimplementation** - java.net reprogramado consimple处理
- [ ] **Dynamic CDS Archives** - Class Data Sharing dinámico

### Java 14 (2020)
- [ ] **Records (Preview)** - `record Point(int x, int y)`, datos inmutables
- [ ] **Pattern Matching for instanceof (Preview)** - `if (obj instanceof String s)`
- [ ] **Text Blocks (Second Preview)** - mejora de indentación
- [ ] **Helpful NullPointerExceptions** - mensajes detallados de NPE
- [ ] **CMS GC removed** - Garbage Collector CMS eliminado

### Java 15 (2020)
- [ ] **Text Blocks (Standard)** - `"""..."""`正式的
- [ ] **Pattern Matching for instanceof (Second Preview)**
- [ ] **Sealed Classes (Preview)** - `sealed class Shape permits Circle, Square`
- [ ] **Hidden Classes** - classes no descubribles por reflection
- [ ] **Nashorn deprecated** - Nashorn JS engine deprecated

### Java 16 (2021)
- [ ] **Pattern Matching for instanceof (Standard)** - type check + cast en uno
- [ ] **Records (Standard)** - records son正式的
- [ ] **Unix-domain Socket Channels** - soporte para sockets AF_UNIX
- [ ] **Foreign Linker API (Preview)** - acceso a código nativo
- [ ] **Vector API (Preview)** - operaciones vectoriales SIMD
- [ ] **C++ 14 language features** - actualizaciones en código fuente Java

### Java 17 (2021) - LTS
- [ ] **Sealed Classes (Standard)** - classes限制 inheritance
- [ ] **Pattern Matching for switch (Preview)** - switch con pattern matching
- [ ] **Records can implement interfaces** - records pueden implementar interfaces
- [ ] **New macOS Rendering** - Apple Metal API para macOS
- [ ] **Deprecate Security Manager** - Security Manager deprecated

### Java 18 (2022)
- [ ] **Pattern Matching for switch (Second Preview)**
- [ ] **Code Snippets in JavaDoc** - `@snippet` en Javadoc
- [ ] **Internet-Address Resolution SPI** - DNS resolver configurable
- [ ] **Foreign Function & Memory API (Preview)** -替代 JNI
- [ ] **Vector API (Second Preview)** - mejoras vectoriales
- [ ] **Deprecate removal warnings** - `@Deprecated(forRemoval=true)`

### Java 19 (2022)
- [ ] **Pattern Matching for switch (Third Preview)**
- [ ] **Record Patterns (Preview)** - `case record Point(int x, int y)`
- [ ] **Virtual Threads (Preview)** - Project Loom, lightweight threads
- [ ] **Foreign Function & Memory API (Second Preview)**
- [ ] **Vector API (Third Preview)**
- [ ] **switch multiple constants** - múltiples constants en un case

### Java 20 (2023)
- [ ] **Virtual Threads (Second Preview)** - mejoras de scoped values
- [ ] **Record Patterns + Pattern Matching** - nesting de patterns
- [ ] **Foreign Function & Memory API (Third Preview)**
- [ ] **Vector API (Fourth Preview)**
- [ ] **Scoped Values (Incubator)** - compartir datos inmutables
- [ ] **Structured Concurrency (Incubator)** - grouping de threads

### Java 21 (2023) - LTS
- [ ] **Virtual Threads (Standard)** - loom: lightweight threads正式的
- [ ] **Pattern Matching for switch (Standard)** - switch expressions con patterns
- [ ] **Record Patterns (Standard)** - record patterns oficial
- [ ] **String Templates (Preview)** - `STR."Hello \{name}"`
- [ ] **Sequenced Collections** - `SequencedCollection`, `SequencedMap`, `SequencedSet`
- [ ] **Unnamed Patterns and Variables** - `case int _:`
- [ ] **Foreign Function & Memory API (Standard)** -替代 JNI
- [ ] **ZGC (Standard)** - ZGC ya no es experimental

### Java 22 (2024)
- [ ] **String Templates (Second Preview)** - mejoras al sistema de templates
- [ ] **Unnamed Patterns and Variables** - unnamed variables con `_`
- [ ] **Class-File API (Preview)** - parsear y generar class files
- [ ] **Foreign Function & Memory API (Second Preview)**
- [ ] **Stream Gatherers (Preview)** - stream transformations personalizadas
- [ ] **Structured Concurrency (Second Preview)** - structured concurrency正式
- [ ] **Implicitly Declared Classes and Instance Main Methods** - scripts Java
- [ ] **Vector API (Seventh/Final Preview)** - última preview antes de estándar

### Java 23 (2024)
- [ ] **String Templates (Third Preview)** -，继续改进
- [ ] **Class-File API (Second Preview)**
- [ ] **Stream Gatherers (Second Preview)**
- [ ] **Foreign Function & Memory API (Third Preview)**
- [ ] **Structured Concurrency (Third Preview)**
- [ ] **Scoped Values (Second Preview)**
- [ ] **Implicitly Declared Classes (Second Preview)**
- [ ] **New Hash-Based Multimap** - nuevo método en Collections

### Java 24 (2025)
- [ ] **Stream Gatherers (Standard)** - stream custom gatherers
- [ ] **Foreign Function & Memory API (Standard)** - API de memoria nativa正式
- [ ] **Scoped Values (Standard)** - scoped values正式
- [ ] **Implicitly Declared Classes (Standard)** - unnamed classes
- [ ] **Class-File API (Standard)** - class file API正式
- [ ] **Vector API (Standard)** - vector operations estándar
- [ ] **Structured Concurrency (Standard)** - structured concurrency正式

### Java 25 (2025) - LTS Preview
- [ ] **Virtual Thread Enhancements** - mejoras a virtual threads
- [ ] **Pattern Matching Improvements** - nuevos patterns
- [ ] **String Templates Enhancements** - mejoras a templates

### Java 26 (2026) - Actual
- [ ] **Nuevas features a confirmar** - features en desarrollo
