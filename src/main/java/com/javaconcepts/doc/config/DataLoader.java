package com.javaconcepts.doc.config;

import com.javaconcepts.doc.entity.Block;
import com.javaconcepts.doc.entity.Concept;
import com.javaconcepts.doc.entity.ConceptQuestion;
import com.javaconcepts.doc.entity.SubConcept;
import com.javaconcepts.doc.entity.SubConceptQuestion;
import com.javaconcepts.doc.repository.ConceptRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class DataLoader implements CommandLineRunner {

    private final ConceptRepository conceptRepository;

    public DataLoader(ConceptRepository conceptRepository) {
        this.conceptRepository = conceptRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        // ===== JAVA CORE =====
        Concept clase = concept("Clase", "clase", Block.JAVA_CORE, 1,
            "Una clase es la plantilla fundamental en Java para crear objetos. Define los atributos (estado) y métodos (comportamiento) que tendrán las instancias. Todo código Java reside dentro de clases. Una clase puede tener modificadores de acceso, y representa el plano fundamental de la programación orientada a objetos.",
            null,
            cq("¿Qué es una clase en Java?",
                "Una clase es una plantilla o molde que define la estructura y el comportamiento de los objetos. Se declara con la palabra clave 'class' y sirve como plano para crear instancias con 'new'. Una clase puede contener campos (atributos), constructores, métodos y clases internas."),
            cq("¿Diferencia entre clase y objeto?",
                "La clase es la definición/plantilla; el objeto es la instancia concreta creada a partir de esa plantilla. La clase describe qué atributos y métodos tendrá cada objeto. El objeto es la realidad en memoria con valores específicos en sus atributos. Ejemplo: la clase 'Persona' define el plano; 'Juan' con nombre='Juan' y edad=25 es un objeto."),
            cq("¿Una clase puede heredar de varias clases?",
                "No, Java no soporta herencia múltiple de clases. Una clase solo puede extender a una única clase padre. Para lograr comportamiento múltiple, Java usa interfaces (una clase puede implementar múltiples interfaces). Esto evita problemas de diamante y mantiene la jerarquía simple.")
        );
        sc(clase, "Constructores", "constructores", 1,
            "Los constructores inicializan objetos. Si no declaras ninguno, Java provee uno sin argumentos (default). Puedes sobrecargar constructores (múltiples con diferentes parámetros). Usa this() para encadenar constructores dentro de la misma clase o super() para llamar al constructor padre.",
            """
            public class Persona {
                private String nombre;

                // Constructor sin argumentos (default si no hay otros)
                public Persona() {
                    this.nombre = "Anónimo";
                }

                // Constructor con parámetros
                public Persona(String nombre) {
                    this.nombre = nombre;
                }

                // Encadenamiento de constructores
                public Persona(String nombre, int edad) {
                    this(nombre); // llama al constructor con 1 parámetro
                }
            }
            """,
            q("¿Qué pasa si no declaras ningún constructor?",
                "Java proporciona automáticamente un constructor sin argumentos (default). Este constructor inicializa los campos con sus valores por defecto (0 para numéricos, null para referencias, false para booleanos). Si declaras cualquier constructor, el default desaparece."),
            q("¿Qué es el encadenamiento de constructores?",
                "Es llamar a otro constructor de la misma clase con this() o al constructor padre con super(). this() debe ser la primera línea. Permite reutilizar lógica entre constructores y evita duplicación de código de inicialización."),
            q("¿Puede un constructor llamar a métodos?",
                "Sí, pero es una práctica de riesgo. Si llamas a un método overridden en el constructor, y la subclase aún no se ha construido completamente, puedes acceder a estado inconsistente. El método se ejecutará con datos parciales del objeto en construcción.")
        );
        sc(clase, "Modificadores de acceso", "modificadores-acceso", 2,
            "Los modificadores controlan la visibilidad de clases, atributos, métodos y constructores. Java tiene 4 niveles: public (todas las clases), protected ( subclases y paquete), default/package-private (solo paquete), private (solo la clase que lo declara).",
            """
            public class Ejemplo {
                public int a;           // accesible desde cualquier lugar
                protected int b;        // accesible desde paquete y subclases
                int c;                  // default: solo paquete
                private int d;           // solo esta clase

                // Método público accesible desde fuera
                public void metodoPublico() {}

                // Método privado: solo uso interno
                private void metodoPrivado() {}
            }
            """,
            q("¿Cuál es el modificador más restrictivo?",
                "private. Limita el acceso exclusivamente a la clase que lo declara. Es el pilar del encapsulamiento: oculta detalles de implementación y protege la integridad del estado interno."),
            q("¿Qué diferencia hay entre default y protected?",
                "default (sin modificador) permite acceso a cualquier clase del mismo paquete. protected va más allá: además permite acceso a subclases aunque estén en otro paquete. Una subclase en otro paquete puede heredar y usar miembros protected, pero no acceder como paquete."),
            q("¿Se puede marcar una clase como private o protected?",
                "Solo como package-private (sin modificador) o public. Una clase top-level no puede ser private ni protected porque eso no tendría sentido: debe ser accesible desde algún contexto. Solo clases internas (inner classes) pueden tener estos modificadores.")
        );
        sc(clase, "Atributos y variables", "atributos-variables", 3,
            "Las variables pueden ser de instancia (cada objeto tiene la suya), de clase/static (compartidas por todas las instancias), o locales (dentro de métodos/constructores). Los atributos son variables de instancia; también existen variables de clase (static) y constantes (final).",
            """
            public class Ejemplo {
                int instancia = 1;           // atributo de instancia
                static int clase = 2;        // variable de clase (static)
                final double PI = 3.1415;   // constante

                public void metodo() {
                    int local = 4;           // variable local
                    // local existe solo dentro del método
                }
            }
            """,
            q("¿Diferencia entre atributo y variable local?",
                "Un atributo tiene ámbito de clase (existe mientras el objeto exista). Una variable local existe solo dentro del método/constructor donde se declara y muere al salir del bloque. Los atributos se inicializan por defecto; las locales deben inicializarse antes de usarse."),
            q("¿Qué significa que un atributo sea final?",
                "Que una vez asignado, no puede ser reasignado. Si es primitivo, su valor no cambia. Si es referencia, la referencia no puede apuntar a otro objeto, pero el objeto interno sí puede modificarse (salvo que sea también inmutable). Una constante de clase debe ser static y final."),
            q("¿Los atributos static pueden ser final?",
                "Sí, y es la forma de definir constantes de clase: static final int MAX_SIZE = 100. Se acceden como Clase.MAX_SIZE (no necesita instancia). Son útiles para valores que no cambian y son comunes a todas las instancias.")
        );
        sc(clase, "Métodos", "metodos", 4,
            "Los métodos definen el comportamiento de una clase. Tienen firma (nombre + parámetros), tipo de retorno, modificador de acceso y cuerpo. Se pueden sobrecargar (mismo nombre, diferentes parámetros) pero no sobreescribir. Varargs permite número variable de argumentos.",
            """
            public class Calculadora {
                // Método sobrecargado
                public int sumar(int a, int b) { return a + b; }
                public double sumar(double a, double b) { return a + b; }

                // Varargs: cualquier número de argumentos
                public int sumar(int... numeros) {
                    int total = 0;
                    for (int n : numeros) total += n;
                    return total;
                }
            }
            """,
            q("¿Qué es la sobrecarga de métodos?",
                "Tener múltiples métodos con el mismo nombre pero diferentes parámetros (tipo, número o ambos) en la misma clase. El compilador decide cuál ejecutar según los argumentos. No es lo mismo que override. Ejemplo: Constructores están sobrecargados por defecto en Java."),
            q("¿Puede un método tener el mismo nombre y parámetros pero diferente tipo de retorno?",
                "No. En Java, la firma del método es nombre + parámetros (no incluye el tipo de retorno). Dos métodos con misma firma pero diferente retorno causan error de compilación. El retorno no es parte de la firma."),
            q("¿Qué son los varargs?",
                "Variable arguments (int... nums). Permiten pasar 0 a N argumentos del mismo tipo. Internamente es un array. Solo puede haber un vararg por método y debe ser el último parámetro. Ejemplo: printf(String format, Object... args) usa varargs.")
        );
        sc(clase, "La palabra clave static", "static", 5,
            "static indica que pertenece a la clase, no a las instancias. Un atributo static es compartido por todos los objetos. Un método static puede llamarse sin instancia. Los bloques static se ejecutan cuando la clase se carga.",
            """
            public class Contador {
                static int instanciaCount = 0;

                static {  // Bloque static: se ejecuta al cargar la clase
                    System.out.println("Clase cargada");
                }

                public Contador() {
                    instanciaCount++;
                }

                public static int getCount() {
                    return instanciaCount;  // puede acceder a static
                }
            }
            """,
            q("¿Un método static puede llamar a un método no static?",
                "No directamente. Dentro de un contexto estático no existe this (no hay instancia). Necesitas una referencia a un objeto para llamar métodos de instancia. static no puede usar this ni super."),
            q("¿Cuándo usar atributos static?",
                "Cuando el valor es común a todas las instancias (contadores, configuraciones, constantes). También cuando no necesitas estado por instancia, solo comportamiento de clase. Cuidado: los static pueden causar memory leaks si Referencias a objetos estáticos evitan que el GC los recoja."),
            q("¿Orden de ejecución: bloque static vs constructor?",
                "El bloque static se ejecuta UNA vez cuando la clase se carga, ANTES de cualquier constructor. Es útil para inicialización compleja de estáticos. El constructor se ejecuta cada vez que haces new, después de que los campos se inicialicen.")
        );
        sc(clase, "La palabra clave final", "final", 6,
            "final evita cambios: en variables, el valor no puede reasignarse; en métodos, no pueden sobreescribirse; en clases, no pueden heredarse. Es inmutabilidad a nivel de lenguaje.",
            """
            public final class Constantes {       // no puede tener hijos
                public static final double PI = 3.14159;

                public final double getPI() {      // no puede overriderse
                    return PI;
                }
            }

            // Esta línea da error:
            // public class MasConstantes extends Constantes {} // COMPILACIÓN ERROR
            """,
            q("¿Diferencia entre final y static final?",
                "final: el valor es constante para cada instancia. static final: una sola copia para toda la clase, existe sin instancia. static final double PI = 3.14 es accesible como Constantes.PI sin crear objeto."),
            q("¿Se puede usar final en parámetros de método?",
                "Sí. Indica que el parámetro no puede ser reasignado dentro del método. No modifica el argumento (es passed by value), pero previene修改 accidental del parámetro dentro del método."),
            q("¿Una clase final puede tener métodos abstract?",
                "No. Una clase final no puede ser extendida, y abstract requiere herencia para ser implementada. Son conceptos mutuamente excluyentes. Si una clase es abstract, no puede ser final.")
        );
        sc(clase, "this y super", "this-super", 7,
            "this referencia al objeto actual. super referencia a la clase padre. Se usan para resolver ambigüedades, encadenar constructores (this()/super());, y acceder a miembros ocultos por herencia.",
            """
            public class Hijo extends Padre {
                private String nombre;

                public Hijo(String nombre) {
                    super();                    // llama al constructor padre
                    this.nombre = nombre;       // distingue atributo de parámetro
                }

                public void mostrar() {
                    System.out.println(super.getMensaje());;  // llama método del padre
                }
            }
            """,
            q("¿Cuándo es obligatorio usar this()?",
                "Cuando tienes sobrecarga de constructores y un constructor necesita invocar a otro con diferentes parámetros. this() debe ser la primera línea del constructor. Si no lo haces explícitamente, Java llama super() implícitamente."),
            q("¿this puede usarse en métodos static?",
                "No. this es una referencia a la instancia actual, y los métodos static no tienen instancia asociada. this no existe en contexto estático. Por eso no puedes usar this en bloques static."),
            q("¿super vs this para acceder a métodos?",
                "super.metodo() llama a la versión del padre (útil en override). this.metodo() llama primero a la versión propia, si no existe baja al padre. Si el método no está override, ambos llaman al mismo.")
        );
        sc(clase, "Clases internas", "clases-internas", 8,
            "Java permite declarar clases dentro de otras: member inner classes (asociadas a instancia), static nested classes, local classes (dentro de método) y anonymous classes (sin nombre, para una sola use). Cada una tiene su propio ámbito y acceso.",
            """
            public class Externa {
                private String outerField = "externo";

                // Clase interna de instancia
                class Interna {
                    void acceder() {
                        System.out.println(outerField);  // acceso automático
                    }
                }

                // Clase anidada estática
                static class Estatica {
                    // No puede acceder a miembros de instancia directamente
                }
            }

            // Uso:
            Externa.Interna i = externa.new Interna();
            Externa.Estatica e = new Externa.Estatica();
            """,
            q("¿Cuándo usar clase interna vs static nested?",
                "Usa static nested cuando la clase auxiliar no necesita acceder a miembros de instancia de la clase externa. Usa inner class (no static) cuando necesitas acceder al contexto de instancia del padre (como un callback que necesita estado del objeto outer)."),
            q("¿Qué es una clase anónima?",
                "Una clase sin nombre que se define e instancia en una sola expresión. Común para implementar interfaces o extender clases de forma inline. new Interface() { @Override void metodo() {} }. Útil para listeners, callbacks, estrategias de un solo uso."),
            q("¿Una clase interna puede ser private?",
                "Sí. Las clases internas pueden tener cualquier modificador de acceso (private, protected, public, default). Esto permite controlar su visibilidad. Una clase interna private es útil para encapsular вспомогательные классы que solo la externa usa.")
        );
        sc(clase, "Enums y Records", "enums-records", 9,
            "enum define un conjunto fijo de constantes. Es una clase especial con constructores privados. Records (Java 16+) son clases inmutables自动生成 getters, equals, hashCode, toString. Ambos son tipos de datos restringidos.",
            """
            public enum Dia {
                LUNES, MARTES, MIERCOLES, JUEVES, VIERNES, SABADO, DOMINGO;

                public boolean esLaborable() {
                    return this != SABADO && this != DOMINGO;
                }
            }

            // Record: clase inmutable automática
            public record Persona(String nombre, int edad) {
                // Constructor, getters (nombre(), edad());,
                // equals, hashCode, toString generados automáticamente

                // Puede tener constructores o métodos adicionales
                public Persona {
                    if (edad < 0) throw new IllegalArgumentException();
                }
            }
            """,
            q("¿Qué es un enum en Java?",
                "Un tipo de dato con un conjunto fijo de valores (LUNES, MARTES...). Internamente es una clase que extiende java.lang.Enum. Puedes añadir métodos, constructores y campos. Cada valor es una instancia única del enum. Ejemplo: Direction.NORTH instanceof Enum es true."),
            q("¿Diferencia entre enum y clase normal?",
                "Un enum tiene instancias predefinidas (LUNES, MARTES...), constructor privado implícito, y no puede ser instanciado con new. Una clase normal puede tener N instancias, todas creadas con new. Enum es para conjuntos cerrados de valores; clase para objetos ilimitados."),
            q("¿Qué es un record en Java?",
                "Un record es una clase inmutable con datos. Automáticamente genera: constructor con parámetros, getters con el nombre del campo (no getX());, equals/hashCode basados en campos, toString legible. Ideal para DTOs y data classes. Reduce boilerplate enormemente.")
        );

        Concept objeto = concept("Objeto", "objeto", Block.JAVA_CORE, 2,
            "Un objeto es una instancia de una clase. Existe en memoria (heap) y tiene identidad propia, estado (valores de sus atributos) y comportamiento (sus métodos). Los objetos se comunican mediante mensajes (llamadas a métodos). En Java, todo excepto tipos primitivos es un objeto.",
            null,
            cq("¿Qué es un objeto en Java?",
                "Un objeto es una instancia concreta de una clase, creada en tiempo de ejecución mediante 'new'. Cada objeto tiene su propia identidad (dirección de memoria única), estado (valores de sus atributos) y comportamiento (métodos que puede ejecutar). Los objetos residen en el heap y son gestionados por el garbage collector cuando ya no hay referencias."),
            cq("¿Qué es el estado de un objeto?",
                "El estado son los valores actuales de todos los atributos (campos) del objeto. Se almacena en los campos de instancia. Dos objetos de la misma clase pueden tener estados diferentes. El estado puede cambiar a lo largo de la vida del objeto mediante la ejecución de métodos que modifican sus atributos."),
            cq("¿Qué es la identidad de un objeto?",
                "La identidad es lo que distingue a un objeto de otro, incluso si tienen el mismo estado. En Java, la identidad está implementada por la referencia en memoria (dirección del heap). Cada objeto tiene una identidad única. La identidad se usa en comparaciones con '==' (compara referencias) y en operaciones de colección.")
        );
        sc(objeto, "Creación y ciclo de vida", "creacion-objetos", 1,
            "Los objetos se crean con new, que invoca el constructor y devuelve una referencia. El GC se encarga de destruir objetos sin referencias. Hay 4 formas de crear: new, deserialización, clone(), reflexión.",
            """
            Persona p = new Persona("Ana");  // new

            // El objeto existe mientras haya referencia p
            // Cuando p = null o sale del ámbito, el objeto es eligible para GC
            p = null;  // objeto anterior ahora es garbage
            """,
            q("¿Qué hace new realmente?",
                "1) Allocation: reserva memoria en heap. 2) Constructor: inicializa el objeto ejecutando el constructor. 3) Reference: devuelve la dirección de memoria (referencia). Si el constructor lanza excepción, el objeto no se considera creado y el GC puede reclamarlo."),
            q("¿Cuándo es eligible para garbage collection?",
                "Cuando no hay ninguna referencia viva apuntando al objeto. Una referencia es 'viva' si está en stack, en un registro de CPU, o en un objeto reachable desde una referencia viva. El GC периодически busca estos objetos y reclamar su memoria."),
            q("¿Puedo destruir un objeto manualmente?",
                "No hay método para forzar destrucción. System.gc() sugiere al GC que ejecute, pero no garantiza nada. En Java moderno con GC buenos (G1, ZGC, Shenandoah), no necesitas forzar. La memoria se gestiona automáticamente.")
        );
        sc(objeto, "equals y hashCode", "equals-hashcode", 2,
            "equals define igualdad lógica; == compara referencias (dirección de memoria). Por defecto, equals de Object usa ==. hashCode genera un int para estructuras hash (HashMap, HashSet). El contrato: objetos iguales deben tener igual hashCode.",
            """
            public class Persona {
                private String dni;
                private String nombre;

                @Override
                public boolean equals(Object o) {
                    if (this == o) return true;
                    if (!(o instanceof Persona p)); return false;
                    return dni.equals(p.dni);  // igualdad por DNI
                }

                @Override
                public int hashCode() {
                    return dni.hashCode();  // consistente con equals
                }
            }
            """,
            q("¿Por qué siempre que overridess equals debes overrider hashCode?",
                "El contrato de hashCode dice: objetos iguales deben producir el mismo hash. Si no lo haces, HashMap y HashSet fallarán: put(equals) puede guardar en cubetas equivocadas, y get(equals) no encontrará objetos. Es una source de bugs sutiles."),
            q("¿equals vs == en objetos?",
                "== compara referencias: true si apuntan al mismo objeto en memoria. equals compara contenido/lógica: true si el significado es igual. Para String, equals compara texto, == compara referencias (puede fallar con interning). Siempre usa equals para contenido, == para identidad."),
            q("¿Qué es el método equals por defecto de Object?",
                "return this == obj; . Compara referencias. Si no overridess, objetos distintos siempre serán diferentes. Esto es correcto para entidades con identity única, pero incorrecto para value objects que deberían ser iguales si tienen los mismos valores.")
        );
        sc(objeto, "toString", "tostring", 3,
            "toString devuelve una representación String del objeto. El default de Object devuelve nombreClase@hashcode (poco útil). Override para mostrar datos útiles en debugging y logs.",
            """
            public class Persona {
                private String nombre;
                private int edad;

                @Override
                public String toString() {
                    return "Persona{nombre=" + nombre + ", edad=" + edad + "}";
                }
            }

            Persona p = new Persona("Ana", 30);
            System.out.println(p);  // Persona{nombre=Ana, edad=30}
            """,
            q("¿Por qué es importante overrider toString?",
                "Los logs, System.out.println, depuradores y excepciones usan toString automáticamente. Un toString bien implementado muestra datos útiles para debugging. El default (ClassName@hash) es inútil. En entidades, muestra identidad; en DTOs/value objects, muestra datos."),
            q("¿Se usa toString en concatenación de strings?",
                "Sí. Java automáticamente llama a toString() cuando concatenas con +. \"edad: \" + persona convierte persona a string usando toString(). Si no override, concatenation usa el default Object.toString()."),
            q("¿Puedo controlar el formato de toString?",
                "Sí, defines el formato tú. Usa String.format(), StringBuilder, oRecords ya generan toString legible por defecto. Para objetos complejos, considera un helper que formatee cada componente.")
        );
        sc(objeto, "Garbage Collector", "garbage-collector", 4,
            "El GC reclaim memoria de objetos sin referencias. No hay destructor manual. El GC decide cuándo y cómo ejecutar. G1GC es el default desde Java 9. Referencias: Strong (normales), Soft (borra antes de OOM), Weak (borra en próximo GC), Phantom (post-GC tracking).",
            """
            // Referencias
            WeakReference<String> ref = new WeakReference<>(new String("dato"));;
            // Si solo esta referencia existe, GC puede reclamarlo

            // SoftReference: útil para caché
            SoftReference<List<Dato>> cache = new SoftReference<>(listaPesada);

            // Evitar memory leaks
            static List<Observador> observadores = new ArrayList<>();
            // Si no se limpian, memoria crece indefinidamente
            """,
            q("¿Cómo evitar memory leaks en Java?",
                "1) Limpiar recursos en finally o try-with-resources. 2) Remover listeners cuando ya no sirven. 3) Evitar static collections grandes. 4) Usar WeakHashMap para caches que dependan de GC. 5) Cerrar streams, conexiones, sockets. 6) null-ear referencias cuando ya no se necesiten (último recurso)."),
            q("¿System.gc() garantiza que el GC se ejecute?",
                "No. Solo es una sugerencia (HINT) al JVM. El GC decide si ejecutar basándose en heuristics. En producción, no dependas de System.gc(). El JVM moderno (G1, ZGC) optimiza automáticamente. Forzar GC puede causar pausas innecesarias."),
            q("¿Qué es reference queue?",
                "Una cola donde el GC encola Referencias cuando el objeto referenciado es recolectado. Útil con PhantomReference para hacer cleanup post-reclamación. SoftReference y WeakReference también pueden asociarse a ReferenceQueue para notificación cuando son invalidadas.")
        );

        Concept herencia = concept("Herencia", "herencia", Block.JAVA_CORE, 3,
            "La herencia permite crear nuevas clases basadas en clases existentes, reutilizando código. La subclase hereda atributos y métodos de la superclase y puede añadir los suyos propios. Java usa 'extends' para heredar de una clase. Es la base de la jerarquía de clases y el polimorfismo.",
            null,
            cq("¿Qué es la herencia en Java?",
                "Mecanismo por el cual una clase (subclase) puede extender a otra clase (superclase) para reutilizar su código. La subclase hereda todos los campos y métodos no privados de la superclase, y puede añadir nuevos o sobrescribir existentes. Se declara con 'extends'. Permite modelar relaciones 'es-un' y reutilizar código."),
            cq("¿Cuál es la diferencia entre extends y implements?",
                "'extends' se usa para heredar de una clase (reutiliza código), mientras que 'implements' se usa para implementar una interfaz (define un contrato). Una clase puede extender solo a una clase, pero puede implementar múltiples interfaces. Las interfaces no tienen estado de instancia; las clases abstractas sí pueden tenerlo."),
            cq("¿Una subclase hereda constructores?",
                "No, los constructores no se heredan. La subclase debe definir sus propios constructores (o usar el default). Si la subclase no define ningún constructor, obtiene un constructor default automático que llama a super(). Si la subclase define constructores, debe invocar explícitamente a super() en la primera línea si la superclase no tiene constructor sin argumentos.")
        );
        sc(herencia, "extends - herencia simple", "extends", 1,
            "extends indica que una clase hereda de otra. En Java, solo herencia simple de clases (una clase padre). Para herencia múltiple, se usan interfaces. La subclase gana todos los miembros heredados excepto constructores y miembros privados del padre.",
            """
            public class Vehiculo {
                protected int velocidad;

                public void acelerar() { velocidad += 10; }
            }

            public class Coche extends Vehiculo {
                private int numPuertas;

                public Coche(int puertas) {
                    this.numPuertas = puertas;
                }
            }

            Coche c = new Coche(4);
            c.acelerar();  // heredado de Vehiculo
            """,
            q("¿Qué miembros hereda una subclase?",
                "Hereda todos los miembros del padre excepto: constructores (no se heredan, se llama super());, métodos privados (no accesibles directamente, pero sí via getters/setters públicos), métodos static (se ocultan, no se overriden). Miembros protected son accesibles en subclase."),
            q("¿Qué es la visibilidad protected en herencia?",
                "Un miembro protected es accesible desde la subclase aunque esté en otro paquete. Es más permisivo que default (paquete) pero más restrictivo que public. Es el modificador apropiado para métodos que quieres compartir con subclases pero no con cualquier clase."),
            q("¿Una clase puede heredar de una clase final?",
                "No. Una clase marcada final no puede ser extendida. public final class String {}. Intentar extends String causa error de compilación. Esto existe para prevenir изменения de comportamiento en clases que no están diseñadas para extensión.")
        );
        sc(herencia, "super", "super-palabra-clave", 2,
            "super referencia la clase padre. Se usa para: llamar al constructor padre (super());, acceder a métodos o atributos ocultados (super.metodo());, y distinguir miembros con igual nombre. Es análogo a this pero para el padre.",
            """
            public class Animal {
                public void hablar() { System.out.println("..."); }
            }

            public class Gato extends Animal {
                @Override
                public void hablar() {
                    System.out.println("Miau");
                }

                public void hablarPadre() {
                    super.hablar();  // llama al hablar() de Animal
                }
            }
            """,
            q("¿super() debe ser la primera línea del constructor?",
                "Sí, si lo escribes explícitamente. Si no lo haces, Java inserta super() sin argumentos automáticamente al inicio. El constructor del padre debe existir y ser accesible (no private). Si el padre no tiene constructor sin args, debes invocar super con parámetros."),
            q("¿Puedo usar super en métodos static?",
                "No. super y this son referencias de instancia. Los métodos static no tienen instancia asociada. No puedes usar super, this, ni nada relacionado con instancia dentro de un método static.")
        );
        sc(herencia, "Override vs Overload", "override-vs-overload", 3,
            "Override (sobreescritura): redefine método del padre en la subclase con相同签名. Requiere @Override, binding dinámico (polimorfismo). Overload (sobrecarga): múltiples métodos con igual nombre pero diferentes parámetros en la misma clase.",
            """
            class Padre {
                void metodo(int x) { System.out.println("Padre int"); }
            }

            class Hija extends Padre {
                @Override
                void metodo(int x) { System.out.println("Hija int"); }  // override

                void metodo(String s) { System.out.println("Hija String"); }  // overload
            }

            new Hija().metodo(1);    // "Hija int" (override)
            new Hija().metodo("a"); // "Hija String" (overload)
            """,
            q("¿Cuál se resuelve en tiempo de compilación vs runtime?",
                "Overload se resuelve en compile-time (binding estático): el compilador elige cuál método según los tipos de argumentos. Override se resuelve en runtime (binding dinámico): la JVM decide cuál método ejecutar según el tipo real del objeto (polimorfismo)."),
            q("¿Puedo overrider un método static?",
                " técnicamente no. Los métodos static no se heredan, se ocultan. La subclase define su propio static con la misma firma, pero no es override真正的 polimórfico. new Hija().metodoStatic() llama al static de Hija; new Padre().metodoStatic() llama al de Padre."),
            q("¿Reglas para override?",
                "1) Misma firma (nombre + parámetros, no incluye retorno). 2) No puede ser más restrictivo (public→protected→private). 3) Puede ser menos restrictivo (private→protected→public). 4) Debe tener return covariant (mismo tipo o subtipo). 5) No puede lanzar nuevas excepciones checked más amplias.")
        );
        sc(herencia, "instanceof y casting", "instanceof-casting", 4,
            "instanceof проверяет тип объекта. instanceof true si el objeto es del tipo especificado o subtipo. El cast (conversión) cambia el tipo de referencia. Sin instanceof, un cast incorrecto lanza ClassCastException.",
            """
            Object obj = new Gato();

            if (obj instanceof Gato) {
                Gato g = (Gato) obj;  // cast seguro
            }

            // Java 16+ pattern matching:
            if (obj instanceof Gato g) {
                g.maullar();  // g ya es Gato, sin cast explícito
            }
            """,
            q("¿Qué es el casting ascendente y descendente?",
                "Ascendente (upcasting): SubClase → ClasePadre. Implícito, seguro. new Gato() puede Treatarse como Animal. Descendente (downcasting): ClasePadre → SubClase. Explícito, puede lanzar ClassCastException. Siempre verificar con instanceof antes."),
            q("¿Qué es el pattern matching de instanceof (Java 16+)?",
                "Permite combinar instanceof y cast en una expresión: if (obj instanceof Gato g) { g.maullar(); }. El compiladorinfiere que g es Gato dentro del bloque. Más conciso, menos error-prone, y elcast es automático. Reemplaza el patrón manual instanceof + cast.")
        );
        sc(herencia, "Composition vs Inheritance", "composition-inheritance", 5,
            "Inheritance (extends): relación ES-UN (Gato ES-UN Animal). Composition (tiene-un): relación TIENE (Coche TIENE Motor). Effective Java: 'Favor composition over inheritance'. Composition es más flexible, menos耦合, evita el проблема del clase base frágil.",
            """
            // Composición: Coche tiene un Motor
            public class Coche {
                private final Motor motor;  // composición

                public Coche(Motor motor) {
                    this.motor = motor;
                }

                public void arrancar() {
                    motor.encender();  // delegation
                }
            }

            // Herencia: Golf es un Coche (¿realmente?)
            public class Golf extends Coche {
                // Golf hereda todo de Coche, pero si Coche cambia, Golf se rompe
            }
            """,
            q("¿Cuándo usar herencia?",
                "Cuando hay una relación clara ES-UN verdadera y la clase padre está diseñada para herencia (documentada, métodos abstractos, no final). Ejemplo: ArrayList extends AbstractList. La relación es permanente y el padre es un contrato estable."),
            q("¿Problemas de herencia excesiva?",
                "1) Acoplamiento fuerte: cambios en padre rompen hijos. 2) Jerarquía rígida: no puedes cambiar padre después. 3) Multiplicación de subclases: explode en combinatorial. 4) Fragile Base Class Problem: cambios aparentemente seguros en padre causan fallos en subclasses."),
            q("¿Cómo implementar composition?",
                "1) Define la funcionalidad como interfaz. 2) Tu clase tiene una referencia a la implementación. 3) Delegas las llamadas. Ejemplo: class Coche { private Motor motor; void arrancar() { motor.encender(); } }. Así puedes cambiar Motor por otro tipo sin afectar Coche.")
        );

        Concept polimorfismo = concept("Polimorfismo", "polimorfismo", Block.JAVA_CORE, 4,
            "El polimorfismo permite que objetos de diferentes clases respondan al mismo mensaje de formas distintas. En Java hay dos tipos: polimorfismo en tiempo de compilación (overload/sobrecarga) y en tiempo de ejecución (override/sobrescritura). Es uno de los pilares de la OOP junto con herencia y encapsulamiento.",
            null,
            cq("¿Qué es el polimorfismo?",
                "Capacidad de un objeto de presentarse como múltiples tipos. Permite que objetos de subclases diferentes se traten como instancias de su clase padre. En runtime, el método correcto se ejecuta basándose en el tipo real del objeto, no en el tipo de la referencia. Esto permite escribir código flexible y extensible."),
            cq("¿Diferencia entre overload y override?",
                "Overload (sobrecarga): mismo nombre, diferentes parámetros. Resuelto en tiempo de compilación. No es verdadero polimorfismo. Override (sobrescritura): método en subclase con misma firma que en superclase. Resuelto en runtime mediante virtual dispatch. Sí es polimorfismo."),
            cq("¿Qué es el upcasting?",
                "Conversión de una referencia de subclase a una referencia de superclase. Es implícito y seguro. Permite tratar una colección de subclases como colección de la clase padre. Ejemplo: 'Animal a = new Gato();' - la referencia es tipo Animal pero el objeto es Gato.")
        );
        sc(polimorfismo, "Polimorfismo en tiempo de compilación", "polimorfismo-compilacion", 1,
            "Overload (polimorfismo estático): múltiples métodos con igual nombre pero diferentes parámetros. El compilador decide cuál método ejecutar basándose en los tipos de argumentos. No es true polymorphism porque no hay cambio de comportamiento en runtime.",
            """
            public class Calculadora {
                public int sumar(int a, int b) {
                    return a + b;
                }

                public double sumar(double a, double b) {
                    return a + b;
                }

                public String sumar(String a, String b) {
                    return a + b;  // concatenación
                }
            }

            Calculadora c = new Calculadora();
            c.sumar(1, 2);       // int overload
            c.sumar(1.5, 2.5);  // double overload
            """,
            q("¿Es el overload verdadero polimorfismo?",
                "Técnicamente no es polimorfismo en runtime. Es resolución de método en tiempo de compilación (static binding). El término correcto es 'overloading' o 'static polymorphism'. A menudo se agrupa con polimorfismo porque el método llamado varía según el contexto de tipos."),
            q("¿Puede el overload retornar diferente tipo?",
                "No. La firma del método es nombre + lista de parámetros (orden y tipos). El retorno no es parte de la firma. Dos métodos con igual nombre y parámetros pero diferente retorno cause error de compilación: 'method already defined'.")
        );
        sc(polimorfismo, "Polimorfismo en tiempo de ejecución", "polimorfismo-runtime", 2,
            "Override (polimorfismo dinámico): la subclase redefine método del padre. En runtime, la JVM detecta el tipo real del objeto y ejecuta el método correspondiente. Es la base del polimorfismo orientado a objetos.",
            """
            public class Animal {
                public void hacerRuido() {
                    System.out.println("...");
                }
            }

            public class Perro extends Animal {
                @Override
                public void hacerRuido() {
                    System.out.println("Guau");
                }
            }

            public class Gato extends Animal {
                @Override
                public void hacerRuido() {
                    System.out.println("Miau");
                }
            }

            Animal a = new Perro();
            a.hacerRuido();  // imprime "Guau" (Late binding)
            """,
            q("¿Qué es late binding?",
                "El método a ejecutar se determina en runtime, no en compile-time. La JVM mira el tipo real del objeto (no el tipo de la referencia) y ejecuta el método overridden correspondiente. Esto permite tratar diferentes objetos uniformemente via referencia padre y obtener comportamiento específico."),
            q("¿abstract vs concrete override?",
                "abstract force override: un método abstract en clase padre debe ser implementado por subclases o la subclase es abstracta. concrete override: la subclase puede o no override un método concreto del padre. Ambos son dynamic polymorphism.")
        );
        sc(polimorfismo, "Interfaces como tipos", "interfaces-como-tipos", 3,
            "Una interfaz define un contrato. Cualquier clase que implemente la interfaz puede ser tratada como ese tipo. Esto permite escribir código desacoplado: tratar colección de múltiples implementaciones uniformemente via la interfaz.",
            """
            public interface Figura {
                double calcularArea();
            }

            public class Circulo implements Figura {
                private double radio;
                @Override
                public double calcularArea() {
                    return Math.PI * radio * radio;
                }
            }

            public class Cuadrado implements Figura {
                private double lado;
                @Override
                public double calcularArea() {
                    return lado * lado;
                }
            }

            // Ambas puedenTreatarse como Figura
            List<Figura> figuras = List.of(new Circulo(1), new Cuadrado(2));;
            figuras.forEach(f -> System.out.println(f.calcularArea()););
            """,
            q("¿Por qué es útil polimorfismo con interfaces?",
                "Permite escribir código reusable contra abstracciones, no concreciones. Si tu método acepta Figura, puedes pasar Circulo, Cuadrado o cualquier figura futura. Agregar nuevas implementaciones no requiere cambiar el código que usa la interfaz (Open/Closed Principle)."),
            q("¿Diferencia entre interfaz y clase abstracta para polimorfismo?",
                "Interfaz: múltiples implementaciones posibles, solo métodos abstractos (y default desde Java 8). Clase abstracta: una jerarquía única, puede tener estado y métodos concretos. Usa interfaz para contratos;p abstract para compartir código base entre subtipos.")
        );

        Concept encapsulamiento = concept("Encapsulamiento", "encapsulamiento", Block.JAVA_CORE, 5,
            "El encapsulamiento oculta los detalles internos de implementación y expone solo lo necesario. Se logra con modificadores de acceso (private, public, protected). Los datos se acceden mediante métodos (getters/setters) en lugar de campos directos. Protege la integridad del estado y permite cambiar la implementación sin afectar a los clientes.",
            null,
            cq("¿Qué es el encapsulamiento?",
                "Principio de diseño que oculta los detalles internos de un objeto y expone solo una interfaz controlada. Se implementa con modificadores de acceso: campos privados + métodos públicos. Previene acceso directo al estado interno, lo que protege la integridad de los datos y permite cambiar la implementación internamente sin afectar a quienes usan la clase."),
            cq("¿Por qué usar getters y setters?",
                "Permiten control sobre cómo se lee y modifica el estado. Un setter puede validar datos antes de guardarlos. Un getter puede computar valores derivados. Sin ellos, cualquier código podría modificar campos directamente, rompiendo invariantes. Permiten cambiar la implementación interna sin cambiar la API pública."),
            cq("¿Encapsulamiento vs abstracción?",
                "Encapsulamiento es 'ocultar cómo funciona' (detalles internos, implementación). Abstracción es 'ocultar qué hace' (mostrar solo interfaz esencial). Encapsulamiento se logra con modificadores de acceso; la abstracción con interfaces y clases abstractas. Trabajan juntos: la abstracción define qué se ve, el encapsulamiento esconde cómo funciona.")
        );
        sc(encapsulamiento, "Getters y setters", "getters-setters", 1,
            "Getters (accessors) leen atributos; setters (mutators) los modifican. Permiten control sobre cómo se accede y modifica el estado. Validación en setters, computed properties con getters. Es convención nombrar getXxx/isXxx para booleanos.",
            """
            public class Persona {
                private String nombre;
                private int edad;

                public String getNombre() { return nombre; }

                public void setNombre(String nombre) {
                    this.nombre = (nombre != null) ? nombre : "Anónimo";
                }

                public int getEdad() { return edad; }

                public void setEdad(int edad) {
                    if (edad < 0) throw new IllegalArgumentException();
                    this.edad = edad;
                }
            }
            """,
            q("¿Getter/setter vs acceso directo public?",
                "public rompe encapsulamiento: cualquier código puede modificar el valor sin control. Getters/setters permiten: validación, logging, computed values, immutable snapshots. Lombok @Data genera automáticamente, pero recuerda que expones todo sin control."),
            q("¿Puedo hacer un getter sin setter?",
                "Sí. El patrón read-only existe: solo getter. Ejemplo: getId() sin setId() (el ID se asigna en creación y no cambia). También puedes tener setter privado para uso interno sin exposición pública.")
        );
        sc(encapsulamiento, "Inmutabilidad", "inmutabilidad", 2,
            "Un objeto inmutable no puede cambiar después de su creación. En Java: clase final, atributos final, sin setters, defensive copying en getters. String es el ejemplo clásico. Beneficios: thread-safe sin sincronización, seguridad en passing por APIs.",
            """
            public final class PersonaInmutable {
                private final String nombre;
                private final int edad;

                public PersonaInmutable(String nombre, int edad) {
                    this.nombre = nombre;  // sin copia
                }

                public String getNombre() { return nombre; }
                public int getEdad() { return edad; }

                // No setters
            }
            """,
            q("¿Cómo crear objeto inmutable?",
                "1) Clase final (no subclases). 2) Todos los campos final. 3) No setters. 4) Si hay campos mutables (Date, Collection), devuelve copia defensiva en getter. 5) Constructor recibe objetos mutables vía copia. 6) No hay métodos que modifiquen estado."),
            q("¿Beneficio principal de inmutabilidad?",
                "Thread safety: múltiples threads pueden leer el mismo objeto sin sincronización porque nunca cambia. No hay race conditions. Simplifica reasoning sobre el código: una vez creado, el objeto es siempre igual.")
        );
        sc(encapsulamiento, "Builder pattern", "builder-pattern", 3,
            "Pattern para construir objetos complejos paso a paso. Útil cuando el constructor tiene muchos parámetros o hay combinaciones de opcionales. El builder tiene métodos fluido que возвращают el builder mismo, y al final build().",
            """
            public class Persona {
                private final String nombre;
                private final int edad;
                private final String ciudad;

                private Persona(Builder b) {
                    this.nombre = b.nombre;
                    this.edad = b.edad;
                    this.ciudad = b.ciudad;
                }

                public static class Builder {
                    private String nombre = "";
                    private int edad = 0;
                    private String ciudad = "";

                    public Builder nombre(String n) { this.nombre = n; return this; }
                    public Builder edad(int e) { this.edad = e; return this; }
                    public Builder ciudad(String c) { this.ciudad = c; return this; }

                    public Persona build() { return new Persona(this); }
                }
            }

            Persona p = new Persona.Builder()
                .nombre("Ana")
                .edad(30)
                .ciudad("Madrid")
                .build();
            """,
            q("¿Por qué no simplemente constructores sobrecargados?",
                "Con 4+ parámetros opcionales, los constructores explotarían en N! combinaciones. El builder clarifica qué параметр es cada uno (naming), soporta defaults, y es más legible. Ej: new Persona(nombre, edad, ciudad, null, null) no dice qué son los nulls."),
            q("¿Builder con inmutabilidad?",
                "El Builder permite set параметры uno a uno, pero el objeto final es inmutable. El constructor privado recibe el builder y copia los valores. Así tienes两个方面: flexibilidad de construcción y seguridad de inmutabilidad.")
        );

        Concept interfaceConcept = concept("Interface", "interface", Block.JAVA_CORE, 6,
            "Una interfaz define un contrato que las clases pueden implementar. Declara métodos abstractos (antes de Java 8, solo eso). Desde Java 8 puede tener métodos default y static. Desde Java 9, métodos private. Una clase puede implementar múltiples interfaces, superando la limitación de herencia única de clases.",
            null,
            cq("¿Qué es una interfaz?",
                "Un contrato que define qué métodos debe implementar una clase, sin especificar cómo. Antes de Java 8 solo tenía métodos abstractos. Desde Java 8 puede tener métodos default (con implementación), static, y desde Java 9 también private. Se declara con 'interface' y una clase la implementa con 'implements'. Permite herencia múltiple en Java."),
            cq("¿Diferencia entre interfaz abstract class?",
                "Interfaz: solo define contrato (métodos abstractos), puede tener métodos default/static/private desde Java 8. Soporta herencia múltiple. No tiene estado de instancia. Abstract class: puede tener estado (campos), constructores, métodos implementados completos. Soporta solo herencia simple. Elegir interfaz cuando hay comportamiento opcional; clase abstracta cuando hay estado común."),
            cq("¿Para qué sirven los default methods?",
                "Permiten añadir métodos a interfaces sin romper implementaciones existentes. El código que implementa la interfaz hereda el default automáticamente. Útil para evolucionar APIs (añadir métodos a interfaces existentes). También permiten implementar patrón mixin. La clase puede override el default si necesita comportamiento específico.")
        );
        sc(interfaceConcept, "Default methods", "default-methods", 1,
            "Java 8+: las interfaces pueden tener métodos con implementación (default methods). Permiten extender interfaces sin romper implementaciones existentes. La clase que implementa puede usar el default o override.",
            """
            public interface Figura {
                double calcularArea();

                default void dibujar() {
                    System.out.println("Dibujando figura genérica");
                }
            }

            public class Circulo implements Figura {
                private double radio;

                @Override
                public double calcularArea() {
                    return Math.PI * radio * radio;
                }

                // No necesita override de dibujar()
            }

            new Circulo().dibujar();  // "Dibujando figura genérica"
            """,
            q("¿Por qué se introdujeron default methods?",
                "Para permitir evolution de interfaces sin romper implementaciones existentes. Ej: agregar stream() a Collection sin afectar a todas las implementaciones. Antes de Java 8, any new method rompía el código existente. Default methods resuelven eso."),
            q("¿Qué es el problema del diamante con default methods?",
                "Si una clase implementa dos interfaces con default methods del mismo nombre, hay ambigüedad. La clase debe override el método冲突 concreto. Ej: interface A { default m(); } interface B { default m(); } class C implements A, B { @Override m() { A.super.m(); } }.")
        );
        sc(interfaceConcept, "Static methods en interfaces", "static-methods", 2,
            "Java 8+: interfaces pueden tener métodos estáticos. Son parte de la API de la interfaz, no del objeto. Se llaman como Interfaz.metodo(), no sobre instancias. Útiles para factories y utility methods relacionados con el tipo.",
            """
            public interface Geometria {
                static double PI = 3.14159;  // implícito final

                static double areaCirculo(double radio) {
                    return PI * radio * radio;
                }
            }

            double area = Geometria.areaCirculo(5);
            """,
            q("¿Diferencia entre static en interface vs clase?",
                "Funcionan igual: принадлежат a la interfaz, accesibles sin instancia. En interface, static es implícitamente final. No pueden usar this ni super (no hay instancia). Sí son heredados por implementaciones (la clase hereda el static como parte del tipo, aunque no puede override)."),
            q("¿Para qué usar static en interface?",
                "Factory methods: crear instancias de implementaciones. Ej: List.of(), Set.of(). Utility methods: funciones de utilidad relacionadas con el tipo. Antes de Java 8, se usaban clases utilitarias (Collections, Arrays). Ahora estos métodos viven en la interfaz.")
        );
        sc(interfaceConcept, "Herencia múltiple", "herencia-multiple", 3,
            "Java permite implementar múltiples interfaces (herencia múltiple de contratos). No hay herencia de estado. Si hay conflictos de default methods, la clase debe resolverlos. Evita el 'diamond problem' de C++.",
            """
            public interface Volador {
                default void volar() { System.out.println("Volando"); }
            }

            public interface Nadador {
                default void nadar() { System.out.println("Nadando"); }
            }

            public class Pato implements Volador, Nadador {
                // Pato puede hacer ambas cosas
            }

            public class Avion implements Volador {
                // Solo volar
            }
            """,
            q("¿Java tiene herencia múltiple de implementación?",
                "No de clases. Solo puede extender una clase (extends). Pero puede implementar múltiples interfaces (implements A, B, C). Esto es herencia múltiple de tipo/contrato, no de estado o implementación (excepto default methods). Es más seguro que la herencia de implementación múltiple de C++.")
        );

        Concept abstractClass = concept("Clase Abstracta", "clase-abstracta", Block.JAVA_CORE, 7,
            "Una clase abstracta es una clase que no puede instanciarse directamente, solo puede heredarse. Puede tener métodos abstractos (sin implementación que las subclases deben proporcionar) y métodos concretos (con implementación). Define una jerarquía donde las subclases completan la implementación. Se declara con la palabra clave 'abstract'.",
            null,
            cq("¿Qué es una clase abstracta?",
                "Una clase declarada con 'abstract' que no puede ser instanciada directamente. Puede contener métodos abstractos (sin cuerpo, que las subclases deben implementar) y métodos concretos (con cuerpo). Sirve como clase base para otras clases. Las subclases concretas deben implementar todos los métodos abstractos o también serán abstractas."),
            cq("¿Diferencia entre clase abstracta e interfaz?",
                "Clase abstracta puede tener estado (campos) y constructores; interfaz no. Una clase puede heredar solo de una abstract class pero implementar múltiples interfaces. Interfaz solo define contratos; abstract class puede proporcionar implementación parcial. Elegir abstract class cuando hay código común compartido; interfaz para definir capacidades."),
            cq("¿Puede una clase abstract tener main?",
                "Sí, una clase abstract puede tener método main() y puede ejecutarse (solo las subclases concretas podrán instanciarse para probarlo). Una clase abstract con main es útil como punto de entrada para testing de métodos concretos heredados, aunque no puedes crear instancia de la clase abstract misma.")
        );
        sc(abstractClass, "Métodos abstractos", "metodos-abstractos", 1,
            "Un método abstracto no tiene implementación; la subclase debe implementarlo. Una clase con al menos un método abstracto debe ser abstract. abstract no puede instanciarse directamente; se crean subclases concretas que implementan los métodos.",
            """
            public abstract class Figura {
                public abstract double calcularArea();  // sin implementación

                public void describir() {  // concreto
                    System.out.println("Área: " + calcularArea());;
                }
            }

            public class Circulo extends Figura {
                private double radio;

                @Override
                public double calcularArea() {  // obligatorio
                    return Math.PI * radio * radio;
                }
            }
            """,
            q("¿abstract vs interface para métodos?",
                "interface: todos los métodos son implícitamente abstractos (antes de Java 8) o pueden ser default. abstract class: puede tener métodos abstractos y concretos mezclados. Usa abstract cuando hay código compartido entre subclases; interface cuando defines纯粹的 contrato."),
            q("¿Puede una clase abstract tener constructor?",
                "Sí, y debe ser llamable desde subclases via super(). Aunque no puedes new Abstracta(), el constructor se ejecuta al crear subclases concretas. Útil para inicializar estado base común.")
        );
        sc(abstractClass, "Cuándo usar clase abstracta", "cuando-abstracta", 2,
            "Usa clase abstract cuando: hay código común para compartir entre subclases, necesitas estado (atributos), o quieres comportamiento concreto por defecto. Usa interfaz cuando: defines纯粹的 contrato, múltiples implementations, no hay código compartido.",
            """
            // Abstracta: comparte código base
            public abstract class Empleado {
                protected double salario;

                public void pagar() {
                    System.out.println("Pagando " + salario);
                }

                public abstract double calcularBonus();  // cada subclase lo define
            }

            public class EmpleadoFijo extends Empleado {
                @Override
                public double calcularBonus() {
                    return salario * 0.1;
                }
            }
            """,
            q("¿Template Method pattern con abstract?",
                "Sí. La clase abstract define el esqueleto del algoritmo (métodos concretos) y delega pasos específicos a subclases (métodos abstractos). Ej: un método procesar() concreto que llama a un método abstract preparar(). Las subclases definen preparar() sin modificar procesar().")
        );

        Concept excepciones = concept("Excepciones", "excepciones", Block.JAVA_CORE, 8,
            "Una excepción es un evento que ocurre durante la ejecución y interrumpe el flujo normal del programa. Java tiene un mecanismo robusto de manejo de excepciones con try-catch-finally y throw/throws. Las excepcioneschecked ( compile-time) deben manejarse o declararse; las unchecked (runtime) no. El stack trace muestra la cadena de llamadas.",
            null,
            cq("¿Diferencia entre Exception y RuntimeException?",
                "Exception (checked): el compilador verifica que sean manejadas o declaradas con throws. RuntimeException (unchecked): no requieren manejo explícito. RuntimeException típicamente indica errores de programación (bugs): NullPointerException, IllegalArgumentException, IndexOutOfBoundsException. Checked exceptions son condiciones recuperables: IOException, SQLException."),
            cq("¿Qué es el stack trace?",
                "Es la traza de llamadas a métodos desde el punto donde se lanzó la excepción hasta el origen. Muestra la secuencia de métodos en ejecución. Comienza con el mensaje de la excepción, seguido de las líneas de código. Útil para debugging: la primera línea relevante (no siempre la causa real) está arriba. La causa raíz puede estar anidada con 'Caused by'."),
            cq("¿Qué es try-with-resources?",
                "Sintaxis Java 7+ que asegura cierre de recursos automáticamente. Declara recursos en el try entre paréntesis; implements AutoCloseable. Al salir del bloque (normal o por excepción), close() se llama automáticamente. Elimina la necesidad de finally para close(). Múltiples recursos separados por punto y coma. Más limpio y seguro que finally.")
        );
        sc(excepciones, "Tipos de excepciones", "tipos-excepciones", 1,
            "Error: fallos de JVM (OutOfMemory), irrecuperables. Exception:，分为 RuntimeException (unchecked) y demás Exception (checked). RuntimeException: bugs de programación (NullPointer, IllegalArgument). Checked: IO, SQL, recuperables.",
            """
            // Checked: el compilador fuerza manejo
            public void leerArchivo() throws IOException {
                FileReader f = new FileReader("fichero.txt");
                // ...
            }

            // Unchecked: no obliga
            public void dividir(int a, int b) {
                if (b == 0) throw new ArithmeticException("división por cero");
            }

            // Error: normalmente no se captura
            public void allocate() {
                throw new OutOfMemoryError("No memory");
            }
            """,
            q("¿Checked vs Unchecked?",
                "Checked: heredan de Exception (no Runtime). El compilador exige try-catch o throws. Para errores recuperables externos (ficheros, BD). Unchecked: heredan de RuntimeException. El compilador no obliga. Para bugs de programación (nulos, argumentos inválidos). Buenos hábitos: checked para recuperable, unchecked para programmer errors."),
            q("¿Error vs Exception?",
                "Error representa problemas graves de la JVM (OutOfMemory, StackOverflow), normalmente irrecuperables. No se espera capturarlos ni manejarlos; si ocurren, la aplicación debería terminar. Exception representa condiciones que una aplicación bien diseñada podría capturar y manejar.")
        );
        sc(excepciones, "Lanzar y capturar", "throw-catch", 2,
            "throw lanza una excepción; el flujo salta al catch más cercano. throws declara excepciones que un método puede lanzar. try-catch-finally atrapa y maneja excepciones. El finally se ejecuta siempre (excepto System.exit).",
            """
            public void ejemplo() {
                try {
                    int resultado = 10 / 0;
                } catch (ArithmeticException e) {
                    System.out.println("División por cero: " + e.getMessage());;
                } finally {
                    System.out.println("Siempre ejecuto");
                }
            }
            """,
            q("¿Orden de catch importa?",
                "Sí. Catch procesa en orden. Más específico primero, más genérico después. Si pones Exception antes que ArithmeticException, el segundo nunca se ejecuta. El compilador marca esto como error unreachable catch block."),
            q("¿Qué pasa si hay excepción en finally?",
                "Si throws en finally, ese exception tiene prioridad sobre cualquier return o excepción del try. El try exception se pierde. Para evitar esto, considera try-with-resources o envolviendo el finally en otro try.")
        );
        sc(excepciones, "Try-with-resources", "try-with-resources", 3,
            "Java 7+: syntax que garantiza cierre de recursos AutoCloseable. El recurso se cierra automáticamente al salir del bloque, incluso si hay excepción. Reemplaza el patrón try-finally manual.",
            """
            // Antes (try-finally manual)
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader("fichero.txt"));;
                return br.readLine();
            } finally {
                if (br != null) br.close();
            }

            // Ahora (try-with-resources)
            try (BufferedReader br = new BufferedReader(
                    new FileReader("fichero.txt"));) {
                return br.readLine();
            }  // br.close() automático
            """,
            q("¿Qué recursos pueden usarse?",
                "Cualquier clase que implemente java.lang.AutoCloseable (o Closeable). Esto incluye: BufferedReader, Connection, FileInputStream, Scanner, etc. Para múltiples recursos: try (Resource1 r1 = ...; Resource2 r2 = ...) { }."),
            q("¿El close() se ejecuta antes o después del finally implícito?",
                "El recurso se cierra antes del finally implícito. El código: try-with-resources { } finalmente { } donde finally es tu bloque finally personal. El orden real: 1) ejecutar try, 2) cerrar recursos, 3) ejecutar finally si existe.")
        );

        Concept genericos = concept("Genéricos", "genericos", Block.JAVA_CORE, 9,
            "Los genéricos permiten escribir código reutilizable que funciona con diferentes tipos sin perder type safety. Se usan parámetros de tipo (como <T>) en clases, interfaces y métodos. El compilador verifica los tipos en tiempo de compilación (type checking) y elimina los casts explícitos (type erasure). Evitan ClassCastException en runtime.",
            null,
            cq("¿Qué es type erasure?",
                "Proceso donde el compilador elimina los parámetros de tipo en tiempo de compilación. <T> se reemplaza por Object o el tipo concreto bounds. Los casts se insertan automáticamente. En runtime, una List<String> y List<Integer> son simplemente List. Esto permite compatibilidad con código pre-genéricos. Pero significa que no puedes hacer 'new T()' o 'new T[10]' directamente."),
            cq("¿Qué es wildcards en genéricos?",
                "Wildcard (?): representa tipo desconocido. Upper bounded (? extends Number): acepta Number o subclases. Unbounded (?): cualquier tipo. Lower bounded (? super Integer): Integer o superclases. '? extends' es para lectura (productor), '? super' es para escritura (consumidor). Principio PECS (Producer Extends, Consumer Super)."),
            cq("¿Diferencia entre List y List<Object>?",
                "List raw type (sin <>) pierde type safety, permite cualquier tipo. List<Object> con genéricos es tipo-safe: el compilador强制检查 que metas tipos correctos. Usar raw types solo para interoperabilidad con código legacy. Siempre preferir List<T> sobre raw List para type safety.")
        );
        sc(genericos, "Clases genéricas", "clases-genericas", 1,
            "Permiten crear clases, interfaces y métodos que operan con tipos unspecified. El tipo se especifica al usar la clase. Ej: List<String> es una Lista de Strings. El compilador genera código específico (type erasure).",
            """
            public class Caja<T> {
                private T contenido;

                public void guardar(T item) { this.contenido = item; }
                public T obtener() { return contenido; }
            }

            Caja<String> cajaStr = new Caja<>();
            cajaStr.guardar("Hola");
            String s = cajaStr.obtener();

            Caja<Integer> cajaInt = new Caja<>();
            cajaInt.guardar(42);
            """,
            q("¿Qué es type erasure?",
                "Los genéricos solo existen en compile-time. El compilador elimina la información de tipo y genera bytecode con casts. En runtime, Caja<String> y Caja<Integer> son la misma clase. Esto permite backward compatibility con código pre-generics."),
            q("¿Puedo crear array de tipo genérico?",
                "No directamente. new T[10] es ilegal porque en runtime no existe T. Soluciones: usar List<T> en lugar de array, o crear array de Object y hacer cast: (T[]) new Object[size] con warning de @SuppressWarnings.")
        );
        sc(genericos, "Wildcards", "wildcards", 2,
            "?: wildcard genérico. ? extends T (covariant): lee datos pero no escribe. ? super T (contravariant): escribe datos pero no гарантирует lectura específica. Sin wildcard: lee y escribe.",
            """
            // ? extends Number: puedo leer como Number (no sé el tipo exacto)
            public double sumar(List<? extends Number> nums) {
                double total = 0;
                for (Number n : nums) {  // lectura OK
                    total += n.doubleValue();
                    // nums.add(5);  // ERROR: no puedo escribir
                }
                return total;
            }

            // ? super Integer: puedo escribir Integer, leer como Object
            public void agregar(List<? super Integer> lista) {
                lista.add(42);  // OK: Integer es subtipo de ? super Integer
                // Number n = lista.get(0);  // OK pero devuelve Object
            }
            """,
            q("PECS: Producer Extends, Consumer Super",
                "Si solo LEES datos de una colección, usa extends (producer). Si solo ESCRIBES datos, usa super (consumer). Si haces ambos, no uses wildcard. Ej: copy(src extends, dest super): src.produce (extends), dest.consume (super)."),
            q("¿Por qué no puedo escribir en ? extends?",
                "Porque no sabes si es List<Integer> o List<Number>. Si pudieras añadir Double a List<? extends Number>, y luego tratar la lista como List<Integer>, romperías type safety. El compiler protege contra esto.")
        );

        Concept colecciones = concept("Colecciones", "colecciones", Block.JAVA_CORE, 10,
            "El framework de colecciones en Java proporciona estructuras de datos para almacenar y manipular grupos de objetos. Incluye interfaces (List, Set, Queue, Map) e implementaciones (ArrayList, HashSet, LinkedList, HashMap...). El paquete es java.util. Permite elegir la estructura adecuada según necesidades de acceso, orden, y rendimiento.",
            null,
            cq("¿Cuándo usar List vs Set?",
                "List: secuencia ordenada, permite duplicados, acceso por índice. Para listas donde el orden importa y hay duplicados. Set: no permite duplicados, no hay orden por índice (salvo LinkedHashSet y TreeSet). Para colección de elementos únicos, eliminar duplicados implícitos, o membership testing."),
            cq("¿Cuándo usar ArrayList vs LinkedList?",
                "ArrayList: acceso aleatorio O(1), inserción/eliminación al final O(1), al inicio O(n). Mejor para acceso frecuente por índice y appends. LinkedList: inserción/eliminación O(1) si tienes iterador, acceso O(n). Mejor para insertions/deletions frecuentes en medio de la lista o como cola (Deque)."),
            cq("¿Qué es Map y cómo se diferencia de Collection?",
                "Map no extiende Collection. Es una colección de pares clave-valor (entries). Cada clave es única. Métodos: put, get, containsKey, containsValue, keySet, values, entrySet. Implementaciones: HashMap (tabla hash, no orden), LinkedHashMap (orden inserción), TreeMap (orden natural o comparator), Hashtable (sincronizado, legacy).")
        );
        sc(colecciones, "List", "list", 1,
            "Interface List: secuencia ordenada con índice. Implementaciones: ArrayList (array dinámico, acceso O(1));, LinkedList (lista enlazada, inserción O(1));, Vector (sincronizado, legacy). Permite duplicados.",
            """
            List<String> array = new ArrayList<>();
            array.add("uno");
            array.add("dos");
            array.get(0);              // "uno" (O(1));
            array.remove(0);           // elimina índice 0 (O(n));
            array.contains("dos");    // true

            List<String> linked = new LinkedList<>();
            linked.addFirst("primero");
            linked.get(0);             // O(n) sin optimización
            """,
            q("¿ArrayList vs LinkedList?",
                "ArrayList: mejor en acceso por índice (O(1));, malo en inserción en medio (O(n));. LinkedList: mejor en inserción/eliminación en medio (O(1) con iterator), malo en acceso por índice (O(n));. En la práctica, ArrayList es casi siempre más rápido por locality de caché CPU."),
            q("¿Qué es fail-fast?",
                "Los iteradores de ArrayList, HashMap, etc. son fail-fast:lancian ConcurrentModificationException si detectan modificación concurrenteenum mientras se itera (excepto via iterator.remove());. No es thread-safe, es simplemente detección de bugs.")
        );
        sc(colecciones, "Set", "set", 2,
            "Interface Set: colección sin elementos duplicados. Implementaciones: HashSet (hash table, O(1), no orden), TreeSet (árbol rojo-negro, O(log n), ordenado), LinkedHashSet (hash + lista enlazada, orden de inserción).",
            """
            Set<String> hash = new HashSet<>();
            hash.add("uno");
            hash.add("uno");  // ignorado: duplicado
            hash.size();       // 1

            Set<String> tree = new TreeSet<>();
            tree.add("z");
            tree.add("a");
            // Iterar: "a", "z" (orden natural)
            """,
            q("¿HashSet.contains es O(1)?",
                "Teóricamente O(1) amortizado. Usa hashCode() para distribuir en buckets. Si hay muchas colisiones, degrada a O(n). Un buen hashCode minimiza colisiones. El 1 es para hasheo correcto y distribución uniforme.")
        );
        sc(colecciones, "Map", "map", 3,
            "Interface Map: clave-valor (no es Collection). Implementaciones: HashMap (O(1) operaciones), TreeMap (O(log n), claves ordenadas), LinkedHashMap (orden de inserción). No permite claves duplicadas.",
            """
            Map<String, Integer> mapa = new HashMap<>();
            mapa.put("uno", 1);
            mapa.put("dos", 2);
            mapa.get("uno");        // 1 (O(1));
            mapa.containsKey("tres"); // false
            mapa.remove("uno");
            mapa.keySet();    // Set de claves
            mapa.values();     // Collection de valores
            mapa.entrySet();   // Set de pares clave-valor
            """,
            q("¿Cómo iterar un Map eficientemente?",
                "Usa entrySet(): for (Map.Entry<K,V> e : mapa.entrySet()); { K k = e.getKey(); V v = e.getValue(); }. Evita get() repetido en cada iteración. entrySet() itera directamente sobre los pares; keySet() requiere get() adicional.")
        );

        Concept streams = concept("Streams", "streams", Block.JAVA_CORE, 11,
            "Stream API (Java 8+) permite procesamiento functional de secuencias de elementos. Un stream no almacena datos; toma datos de una fuente y los procesa a través de una pipeline de operaciones. Hay operaciones intermedias (lazy, devuelven Stream) y terminales (eager, devuelven resultado). No modifica la colección origen.",
            null,
            cq("¿Qué es un Stream y cómo se diferencia de una Collection?",
                "Collection es una estructura de datos que almacena elementos. Stream es una vista lazy para procesamiento de datos. Collection itera externamente (tú controlas el loop); Stream procesa internamente. Stream puede consumirse solo una vez. Stream no modifica la fuente original. Collection es eager; Stream es lazy hasta operación terminal."),
            cq("¿Qué significa que Stream sea lazy?",
                "Las operaciones intermedias (filter, map, etc.) no se ejecutan inmediatamente. Solo construyen el pipeline de operaciones. La ejecución real ocurre cuando se invoca una operación terminal (collect, forEach, reduce...). Esto permite optimizaciones: el stream puede hacer short-circuit (limit) y evitar procesar todo. Intermediate operations son lazy; terminal operations son eager."),
            cq("¿Qué es short-circuiting en streams?",
                "Operaciones que evitan procesar todos los elementos. 'limit(n)' y 'findFirst()' en streams infinitos devuelven resultado finito. 'anyMatch', 'allMatch', 'noneMatch' pueden parar temprano. Tambien 'skip()'跳过 elementos. Permite trabajar con streams infinitos y mejora rendimiento al parar tan pronto como se cumple la condición.")
        );
        sc(streams, "Operaciones intermedias", "streams-intermedias", 1,
            "Operaciones intermedias (filter, map, flatMap, distinct, sorted, limit, skip): devuelven Stream nuevo, son lazy (no se ejecutan hasta operación terminal). Permiten encadenar transforms.",
            """
            List<String> nombres = List.of("Ana", "Pedro", "María", "Juan", "Ana");

            List<String> filtrados = nombres.stream()
                .filter(n -> n.length() > 4)      // reduce a los que cumplen
                .map(String::toUpperCase)          // transforma cada uno
                .distinct()                        // elimina duplicados
                .sorted()                           // ordena
                .collect(Collectors.toList());;      // operación terminal

            // Resultado: [MARÍA, PEDRO, JUAN]
            """,
            q("¿Por qué stream es lazy?",
                "Hasta que no se llama operación terminal, no pasa nada. El pipeline de operaciones intermedias construye un grafo de operaciones. La terminal dispara la ejecución, y el stream optimiza (ej: short-circuit como findFirst sin procesar todo). Esto permite encadenar muchas operaciones sin coste.")
        );
        sc(streams, "Operaciones terminales", "streams-terminales", 2,
            "Operaciones terminales (collect, forEach, reduce, min, max, count, toList, averaging...): disparan la ejecución, devuelven resultado no Stream (o void). Sin terminal, el stream no hace nada.",
            """
            List<Integer> nums = List.of(1, 2, 3, 4, 5);

            // collect: acumula a colección
            List<Integer> lista = nums.stream().filter(n -> n > 2).toList();

            // reduce: combina elementos
            int suma = nums.stream().reduce(0, Integer::sum);

            // forEach: efecto secundario
            nums.stream().forEach(System.out::println);

            // groupingBy: agrupa
            Map<String, List<Persona>> porCiudad = personas.stream()
                .collect(Collectors.groupingBy(Persona::getCiudad));;
            """,
            q("¿collect vs toList()?",
                "toList() (Java 16+) crea lista inmutable directamente. collect(Collectors.toList()); crea ArrayList mutable y luego la envuelve. toList() es más conciso y preferido para casos simples. collect() es más flexible para tipos concretos y operaciones complejas.")
        );
        sc(streams, "Optional en streams", "optional-streams", 3,
            "Optional stream reduce puede devolver Optional si no hay elementos. findFirst(), findAny(), min(), max() возвращают Optional. Permite tratar ausencia sin null.",
            """
            List<Integer> nums = List.of(3, 1, 4, 1, 5);

            Optional<Integer> primero = nums.stream()
                .filter(n -> n > 10)
                .findFirst();
                // empty Optional porque ningún número > 10

            primero.ifPresentOrElse(
                n -> System.out.println("Encontrado: " + n),
                () -> System.out.println("No encontrado")
            );
            """,
            q("¿Qué pasa si stream vacío llama findFirst()?",
                "Devuelve Optional.empty(). No lanza excepción. Es la forma segura de buscar en colección potencialmente vacía. Debes verificar con isPresent() o usar orElse(), orElseGet(), orElseThrow().")
        );

        // ===== PLATAFORMA JAVA =====
        Concept plataformaJava = concept("Plataforma Java", "plataforma-java", Block.JAVA_CORE, 12,
            "Java es una plataforma de desarrollo y ejecución compuesta por el lenguaje Java, el JDK, la JVM y un amplio ecosistema de librerías. Su lema 'write once, run anywhere' se basa en compilar a bytecode que ejecuta la JVM en cualquier sistema operativo.",
            null,
            cq("¿Qué es el JDK?",
                "Java Development Kit: kit de desarrollo que incluye el compilador (javac), la JRE, utilidades (javadoc, jar, jdb) y las librerías estándar. Necesario para desarrollar aplicaciones Java. A partir de Java 11 la JRE ya no se distribuye por separado; el JDK contiene todo."),
            cq("¿Qué es la JVM?",
                "Java Virtual Machine: máquina virtual que ejecuta el bytecode Java. Es la razón de la portabilidad de Java. Cada sistema operativo tiene su propia implementación de JVM, pero el bytecode es el mismo. Gestiona memoria, garbage collection y optimización JIT."),
            cq("¿Qué diferencia hay entre JDK, JRE y JVM?",
                "JVM ejecuta bytecode. JRE (Java Runtime Environment) = JVM + librerías core necesarias para ejecutar apps. JDK = JRE + herramientas de desarrollo (compilador, debugger, etc.). Desde Java 9/11 el modelo cambió: ahora solo existe el JDK modularizado.")
        );
        sc(plataformaJava, "JDK, JRE y JVM", "jdk-jre-jvm", 1,
            "Tres componentes fundamentales de la plataforma Java. La JVM ejecuta bytecode, la JRE proporciona el entorno de ejecución y el JDK añade herramientas de desarrollo. Desde Java 11 el JDK es la única distribución oficial; ya no hay JRE separada.",
            """
            // Compilar con JDK
            javac HolaMundo.java

            // Ejecutar con JRE/JDK (la JVM carga HolaMundo.class)
            java HolaMundo

            // Ver versión del JDK
            java -version
            javac -version
            """,
            q("¿Para qué sirve javac?",
                "Es el compilador de Java. Transforma código fuente .java en bytecode .class. El bytecode es intermedio: no es código máquina nativo, sino instrucciones que la JVM interpreta o compila a código nativo con JIT."),
            q("¿Se puede ejecutar Java sin JDK?",
                "Solo necesitas un JRE/JDK para ejecutar. En producción bastaba con JRE antes de Java 11; ahora se usa un JDK completo o un runtime reducido creado con jlink. Para desarrollar siempre necesitas JDK."));
        sc(plataformaJava, "Versiones de Java", "versiones-java", 2,
            "Java evoluciona con un nuevo release cada 6 meses y versiones LTS (Long Term Support) cada 2 años. Java 8, 11, 17 y 21 son LTS populares. Cada versión añade mejoras en sintaxis, APIs, rendimiento y seguridad.",
            """
            // Versiones LTS importantes
            Java 8  (2014): lambdas, streams, Optional, nueva API fecha
            Java 11 (2018): var en lambdas, HTTP client, modularidad estable
            Java 17 (2021): sealed classes, pattern matching for switch preview
            Java 21 (2023): virtual threads, sequenced collections, record patterns

            // Verificar versión en código
            Runtime.version();
            """,
            q("¿Qué es una versión LTS?",
                "Long Term Support: versión con soporte extendido (actualizaciones de seguridad y bugfixes) durante varios años. Las empresas prefieren LTS para producción porque reduce riesgo. Versiones no-LTS reciben soporte solo 6 meses."),
            q("¿Cuáles son las LTS más usadas?",
                "Java 8, 11, 17 y 21. Java 8 sigue muy presente por compatibilidad. Java 17 es la LTS más adoptada recientemente. Java 21 añade virtual threads, muy relevante para apps concurrentes."));
        sc(plataformaJava, "Bytecode y ciclo de ejecución", "bytecode-ciclo-ejecucion", 3,
            "El código fuente Java se compila a bytecode (.class). La JVM carga, verifica, interpreta y compila a código nativo mediante JIT (Just-In-Time). El ClassLoader carga las clases bajo demanda.",
            """
            // Fuente
            public class Main {
                public static void main(String[] args) {
                    System.out.println("Hola");
                }
            }

            // Compilación
            // javac Main.java  → genera Main.class (bytecode)

            // Ejecución
            // java Main        → JVM carga Main.class, verifica, interpreta y JIT compila
            """,
            q("¿Qué es el JIT compiler?",
                "Just-In-Time compiler: parte de la JVM que compila bytecode a código máquina nativo en tiempo de ejecución para los métodos más usados (hot spots). Mejora el rendimiento respecto a la interpretación pura."),
            q("¿Qué es el ClassLoader?",
                "Componente de la JVM que carga dinámicamente las clases en memoria cuando se necesitan. Existen Bootstrap, Extension y Application ClassLoader. Permite carga dinámica de plugins y frameworks."));
        sc(plataformaJava, "JAR y Module System", "jar-module-system", 4,
            "JAR (Java ARchive) empaqueta clases compiladas y recursos en un único fichero .jar. Desde Java 9 existe el Java Platform Module System (JPMS) con el fichero module-info.java para declarar dependencias y exports explícitos.",
            """
            # Empaquetar en JAR
            jar cvf miapp.jar -C bin .

            # Ejecutar JAR con manifest
            java -jar miapp.jar

            // module-info.java (Java 9+)
            module com.ejemplo.miapp {
                requires java.base;
                requires java.sql;
                exports com.ejemplo.miapp.api;
            }
            """,
            q("¿Qué es un JAR ejecutable?",
                "Un JAR que contiene un MANIFEST.MF con Main-Class definida. Permite ejecutar la aplicación con java -jar sin especificar la clase main. Spring Boot genera JARs ejecutables con todas las dependencias empaquetadas."),
            q("¿Para qué sirve module-info.java?",
                "Declara un módulo JPMS: su nombre, dependencias (requires), paquetes exportados (exports) y servicios. Mejora encapsulamiento, reduce tamaño de runtime con jlink y clarifica dependencias entre librerías."));

        // ===== SPRING =====
        Concept springFramework = concept("Spring Framework", "spring-framework", Block.SPRING, 1,
            "Spring Framework es la base del ecosistema Spring. Proporciona un contenedor ligero de Inversión de Control (IoC), inyección de dependencias, AOP, MVC, acceso a datos, seguridad y testing. Es modular: usas solo los módulos que necesitas. Spring Boot se construye sobre Spring Framework para facilitar la configuración.",
            null,
            cq("¿Qué es Spring Framework?",
                "Es un framework modular para desarrollo empresarial en Java. Su núcleo es el contenedor IoC que gestiona beans y dependencias. A su alrededor hay módulos para web (MVC), datos (JDBC, JPA, Transactions), seguridad, AOP y testing. No es un servidor de aplicaciones, se ejecuta dentro de una app."),
            cq("¿Qué es ApplicationContext?",
                "Es la interfaz principal del contenedor Spring. Representa el contexto de aplicación que gestiona la configuración, los beans y sus dependencias. Implementaciones comunes: AnnotationConfigApplicationContext (Java config), ClassPathXmlApplicationContext (XML) y Spring Boot crea automáticamente un ConfigurableApplicationContext."),
            cq("¿Qué módulos tiene Spring Framework?",
                "Core (IoC, DI), Beans, Context, SpEL, AOP, Aspects, Instrumentation, Messaging, Data Access/Integration (JDBC, JPA, Transactions), Web (MVC, WebFlux), Test y más. Cada módulo es un jar separado que puedes incluir según necesidades.")
        );
        sc(springFramework, "ApplicationContext", "application-context", 1,
            "El ApplicationContext carga la configuración de beans, resuelve dependencias y gestiona el ciclo de vida. En aplicaciones Spring Boot se crea automáticamente con SpringApplication.run(). Puedes obtenerlo por inyección o con ApplicationContextAware.",
            """
            @Service
            public class MiServicio implements ApplicationContextAware {
                private ApplicationContext ctx;

                @Override
                public void setApplicationContext(ApplicationContext ctx) {
                    this.ctx = ctx;
                }

                public void usarBean(String nombre) {
                    MiBean bean = ctx.getBean(MiBean.class);
                }
            }
            """,
            q("¿Cómo obtengo un bean del ApplicationContext?",
                "Por inyección de dependencias (recomendado) o llamando a ctx.getBean(Class<T>) / getBean(String, Class<T>). Evita getBean() en código de negocio porque rompe la inversión de control; úsalo solo en puntos de integración, tests o código de infraestructura."),
            q("¿Diferencia BeanFactory y ApplicationContext?",
                "BeanFactory es el contenedor básico; ApplicationContext extiende BeanFactory añadiendo soporte para internacionalización (i18n), eventos, carga de recursos y resolución de mensajes. En práctica siempre se usa ApplicationContext.")
        );
        sc(springFramework, "Spring AOP", "spring-aop", 2,
            "AOP (Aspect Oriented Programming) permite separar cross-cutting concerns como logging, transacciones o seguridad. Spring AOP usa proxies JDK dinámicos (interfaces) o CGLIB (clases). Se configura con @Aspect, @Before, @After, @Around, @Pointcut.",
            """
            @Aspect
            @Component
            public class LoggingAspect {
                @Before("execution(* com.ejemplo.servicio.*.*(..));")
                public void logBefore(JoinPoint jp) {
                    System.out.println("Método: " + jp.getSignature().getName());;
                }
            }

            // Habilitar: @EnableAspectJAutoProxy
            """,
            q("¿Qué es un Advice en AOP?",
                "Es el código que se ejecuta en un join point. Tipos: Before (antes), After (después), AfterReturning (tras éxito), AfterThrowing (tras excepción) y Around (envuelve la ejecución). Around es el más potente porque puede decidir si ejecuta el método original."),
            q("¿Qué es un Pointcut?",
                "Expresión que define dónde se aplica el advice. Spring soporta sintaxis AspectJ: execution(* com.servicio.*.*(..));, @annotation(MiAnotacion), within(com.servicio..*). Un pointcut selecciona join points (normalmente llamadas a métodos).")
        );
        sc(springFramework, "Spring Data JPA", "spring-data-jpa", 3,
            "Spring Data JPA simplifica el acceso a datos creando repositorios a partir de interfaces. Extiende JpaRepository para obtener CRUD, paginación y ordenación. También deriva queries a partir del nombre del método y soporta @Query con JPQL nativo.",
            """
            public interface ClienteRepository extends JpaRepository<Cliente, Long> {
                List<Cliente> findByCiudad(String ciudad);

                @Query("SELECT c FROM Cliente c WHERE c.activo = true")
                List<Cliente> findActivos();
            }
            """,
            q("¿Qué métodos hereda JpaRepository?",
                "save, findById, findAll, deleteById, delete, count, existsById y más. También incluye métodos de PagingAndSortingRepository como findAll(Pageable). No necesitas implementar la interfaz; Spring genera la implementación en tiempo de ejecución."),
            q("¿Cómo funciona la derivación de queries?",
                "Spring Data parsea el nombre del método (findBy, deleteBy, countBy) seguido de propiedades de la entidad. findByNombreAndActivoTrue genera una query WHERE nombre = ? AND activo = true. Para consultas complejas, usa @Query.")
        );

        Concept ioc = concept("IoC Container", "ioc-container", Block.SPRING, 2,
            "Inversión de Control (IoC) es el principio de delegar la creación y gestión de objetos al contenedor Spring. En lugar de crear dependencias con new, Spring las inyecta. El contenedor gestiona el ciclo de vida de los beans: creación, configuración, inicialización y destrucción.",
            null,
            cq("¿Qué es un bean en Spring?",
                "Un objeto gestionado por el contenedor Spring IoC. Puede ser creado por anotación (@Component, @Service, @Repository, @Controller) o declarado explícitamente con @Bean en una clase @Configuration. El contenedor controla su ciclo de vida y scope."),
            cq("¿Qué es la inyección de dependencias?",
                "Patrón donde Spring provee las dependencias que un objeto necesita en lugar de que el objeto las cree. Ventajas: desacoplamiento, testabilidad (mocks), y reutilización. Spring soporta inyección por constructor (recomendada), por setter y por campo."),
            cq("¿Cuál es el scope por defecto de un bean?",
                "singleton. Spring crea una única instancia por contenedor y la reutiliza. Otros scopes: prototype (nueva instancia cada vez), request (una por petición HTTP), session (una por sesión HTTP), application (una por ServletContext). El scope se define con @Scope.")
        );
        sc(ioc, "Component, Service, Repository", "stereotypes", 1,
            "@Component es la anotación base. @Service, @Repository y @Controller son especializaciones semánticas de @Component para capas de servicio, persistencia y presentación. Spring las detecta con component scanning.",
            """
            @Service
            public class ClienteService {
                private final ClienteRepository repo;

                // Inyección por constructor (preferida)
                public ClienteService(ClienteRepository repo) {
                    this.repo = repo;
                }
            }

            @Repository
            public interface ClienteRepository extends JpaRepository<Cliente, Long> {}
            """,
            q("¿Diferencia @Component y @Service?",
                "Funcionalmente son equivalentes: ambos registran el bean. La diferencia es semántica. @Service indica lógica de negocio y permite que AOP/tx se aplique mejor (aunque @Transactional funciona con cualquiera). @Repository también traduce excepciones de persistencia a DataAccessException."),
            q("¿Por qué preferir inyección por constructor?",
                "Garantiza que las dependencias requeridas existan al crear el objeto (inmutabilidad posible con final). Facilita tests unitarios (puedes pasar mocks). Evita nulls en dependencias. Es el mecanismo recomendado por Spring. Las dependencias opcionales pueden ir por setter.")
        );
        sc(ioc, "Configuration y Bean", "configuration-bean", 2,
            "@Configuration marca una clase como fuente de definiciones de beans. Los métodos @Bean dentro de ella devuelven objetos que Spring gestiona. Útil para clases de terceros que no puedes anotar con @Component.",
            """
            @Configuration
            public class AppConfig {

                @Bean
                public RestTemplate restTemplate() {
                    return new RestTemplate();
                }

                @Bean
                public ObjectMapper objectMapper() {
                    return new ObjectMapper();
                }
            }
            """,
            q("¿Cuándo usar @Bean en lugar de @Component?",
                "Cuando no controlas la clase que quieres convertir en bean (librerías de terceros como RestTemplate, ObjectMapper, DataSource). Si es tu propia clase, preferir @Component o sus especializaciones. @Bean también permite configuración condicional con @Profile y @Conditional."),
            q("¿Qué son los proxy de @Configuration?",
                "Spring envuelve las clases @Configuration con CGLIB proxy para que las llamadas entre métodos @Bean respeten el scope y devuelvan la misma instancia singleton. Si no usas proxy, cada llamada a un @Bean crea una nueva instancia. Puedes desactivar con @Configuration(proxyBeanMethods=false).")
        );
        sc(ioc, "Autowired, Primary, Qualifier", "autowired-qualifier", 3,
            "@Autowired le dice a Spring que inyecte un bean. Si hay múltiples candidatos del mismo tipo, se usa @Primary para marcar el preferido, o @Qualifier para especificar el nombre exacto.",
            """
            @Primary
            @Service
            public class EmailNotificador implements Notificador {}

            @Service
            public class SmsNotificador implements Notificador {}

            // Inyecta EmailNotificador por @Primary
            @Autowired
            private Notificador notificador;

            // O usa qualifier explícito
            @Autowired
            @Qualifier("smsNotificador")
            private Notificador sms;
            """,
            q("¿Qué pasa si hay dos beans del mismo tipo sin @Primary ni @Qualifier?",
                "Spring lanza NoUniqueBeanDefinitionException porque no sabe cuál elegir. Soluciones: marcar uno con @Primary, usar @Qualifier, o inyectar por nombre. Con inyección por constructor, también puedes usar @Qualifier en el parámetro."),
            q("¿Es obligatorio @Autowired con inyección por constructor?",
                "Desde Spring 4.3, no es necesario en constructores con un único constructor. Spring infiere automáticamente que debe inyectar. Buena práctica: dejar un solo constructor y omitir @Autowired para reducir boilerplate. Si hay múltiples constructores, sí debes marcar el elegido.")
        );

        Concept springMvc = concept("Spring MVC", "spring-mvc", Block.SPRING, 3,
            "Spring MVC es el framework web de Spring basado en el patrón Modelo-Vista-Controlador. En APIs REST se usa @RestController que combina @Controller y @ResponseBody. Mapea peticiones HTTP a métodos Java mediante anotaciones como @GetMapping, @PostMapping, etc.",
            null,
            cq("¿Diferencia @Controller y @RestController?",
                "@Controller devuelve el nombre de una vista (template) por defecto. @RestController es @Controller + @ResponseBody, lo que hace que el retorno del método se serialice directamente al cuerpo de la respuesta HTTP (JSON/XML). Usa @RestController para APIs REST."),
            cq("¿Qué es DispatcherServlet?",
                "Es el servlet frontal de Spring MVC que recibe todas las peticiones y las delega al controlador adecuado basándose en los @RequestMapping. Spring Boot lo configura automáticamente. Es el corazón del request handling en Spring MVC."),
            cq("¿Qué es ResponseEntity?",
                "Clase que representa la respuesta HTTP completa: status code, headers y body. Permite control total sobre la respuesta. Ejemplo: ResponseEntity.ok(body), ResponseEntity.status(HttpStatus.CREATED).body(body), ResponseEntity.notFound().build().")
        );
        sc(springMvc, "Mapping anotaciones", "mapping-anotaciones", 1,
            "@RequestMapping mapea peticiones. @GetMapping, @PostMapping, @PutMapping, @DeleteMapping, @PatchMapping son especializaciones para métodos HTTP. Puedes definir path, produces, consumes, params y headers.",
            """
            @RestController
            @RequestMapping("/api/clientes")
            public class ClienteController {

                @GetMapping
                public List<Cliente> listar() { ... }

                @GetMapping("/{id}")
                public Cliente obtener(@PathVariable Long id) { ... }

                @PostMapping
                public ResponseEntity<Cliente> crear(@RequestBody @Valid ClienteDTO dto) { ... }
            }
            """,
            q("¿Puedo poner @RequestMapping en un método?",
                "Sí, y es útil cuando necesitas configuración más detallada. Los métodos específicos (@GetMapping, @PostMapping...) son más concisos y recomendados. Si mezclas, el path se combina con el de clase. El method de @RequestMapping debe coincidir con la petición HTTP."),
            q("¿Qué es produces/consumes?",
                "produces indica el Content-Type de la respuesta (ej: MediaType.APPLICATION_JSON_VALUE). consumes indica qué Content-Type acepta el método en el request body. Ayudan a content negotiation y a rechazar peticiones con formatos no soportados.")
        );
        sc(springMvc, "PathVariable y RequestParam", "pathvariable-requestparam", 2,
            "@PathVariable extrae valores de la URL. @RequestParam extrae parámetros de query string o form data. Ambos pueden ser requeridos u opcionales, y convertir tipos automáticamente.",
            """
            @GetMapping("/clientes/{id}/pedidos")
            public List<Pedido> pedidos(
                    @PathVariable Long id,
                    @RequestParam(defaultValue = "0") int pagina,
                    @RequestParam(defaultValue = "10") int tamaño,
                    @RequestParam(required = false) String estado) {
                return service.buscarPedidos(id, pagina, tamaño, estado);
            }
            // URL: /clientes/5/pedidos?pagina=0&tamaño=20&estado=PENDIENTE
            """,
            q("¿Diferencia @PathVariable y @RequestParam?",
                "@PathVariable toma valores de la ruta URL (/usuarios/{id} → id=5). @RequestParam toma valores de query string (/usuarios?activo=true). Path variables son parte de la URL; query params son opciones/modificadores. Path variables no pueden omitirse; query params pueden ser opcionales con required=false."),
            q("¿Cómo hacer un parámetro opcional?",
                "Con @RequestParam(required = false) o usando defaultValue. Para tipos primitivos, mejor defaultValue para evitar null. También puedes usar Optional<T> como tipo: @RequestParam Optional<String> nombre. Spring inyecta Optional.empty() si falta.")
        );
        sc(springMvc, "RequestBody y ResponseBody", "requestbody-responsebody", 3,
            "@RequestBody deserializa el cuerpo de la petición JSON/XML a un objeto Java. @ResponseBody (implícito en @RestController) serializa el valor de retorno al cuerpo de la respuesta. Spring usa Jackson por defecto para JSON.",
            """
            @PostMapping("/clientes")
            public ResponseEntity<Cliente> crear(@RequestBody @Valid ClienteDTO dto) {
                Cliente creado = service.crear(dto);
                URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{id}")
                    .buildAndExpand(creado.getId());.toUri();
                return ResponseEntity.created(location).body(creado);
            }
            """,
            q("¿Para qué sirve @Valid con @RequestBody?",
                "Activa la validación JSR-303/380 (Bean Validation) sobre el objeto deserializado. Si falla alguna restricción (@NotNull, @Size...), Spring lanza MethodArgumentNotValidException. Se captura con @ExceptionHandler o @ControllerAdvice para devolver errores estructurados (400 Bad Request)."),
            q("¿Cómo devolver solo un mensaje de error?",
                "Puedes devolver ResponseEntity con el mensaje y el status. O usar un DTO de error global con @ControllerAdvice que capture excepciones y devuelva un body consistente. Esto evita mezclar lógica de negocio con manejo de errores en cada controlador.")
        );
        sc(springMvc, "ControllerAdvice y ExceptionHandler", "controller-advice", 4,
            "@ControllerAdvice permite manejar excepciones de forma global para múltiples controladores. @ExceptionHandler dentro del advice captura excepciones específicas y devuelve respuestas controladas.",
            """
            @RestControllerAdvice
            public class GlobalExceptionHandler {

                @ExceptionHandler(ClienteNotFoundException.class)
                public ResponseEntity<ErrorResponse> handleNotFound(ClienteNotFoundException ex) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse(ex.getMessage(), LocalDateTime.now()););
                }

                @ExceptionHandler(MethodArgumentNotValidException.class)
                public ResponseEntity<Map<String, String>> handleValidationErrors(
                        MethodArgumentNotValidException ex) {
                    Map<String, String> errors = new HashMap<>();
                    ex.getBindingResult().getFieldErrors().forEach(e ->
                        errors.put(e.getField(), e.getDefaultMessage()););
                    return ResponseEntity.badRequest().body(errors);
                }
            }
            """,
            q("¿Diferencia @ControllerAdvice y @RestControllerAdvice?",
                "@RestControllerAdvice es @ControllerAdvice + @ResponseBody. Es equivalente a usar @ControllerAdvice en una clase cuyos métodos están anotados con @ResponseBody. Para APIs REST, usa @RestControllerAdvice para no repetir @ResponseBody en cada @ExceptionHandler."),
            q("¿Puede un @ExceptionHandler estar en un controlador?",
                "Sí, y solo manejará excepciones lanzadas por métodos de ese controlador. Si necesitas manejo global, ponlo en @ControllerAdvice. La resolución busca primero en el controlador local, luego en los advice."),
            q("¿Cómo devolver un error 400 con mensaje personalizado?",
                "Captura la excepción con @ExceptionHandler y devuelve ResponseEntity.badRequest().body(...). Para validaciones, MethodArgumentNotValidException tiene el BindingResult con los errores por campo. Construye un mapa o DTO de error y devuélvelo con 400.")
        );

        Concept springSecurity = concept("Spring Security", "spring-security", Block.SPRING, 4,
            "Spring Security es un framework de autenticación y autorización. Se integra con Spring Boot mediante un filter chain que intercepta peticiones. Soporta form login, HTTP Basic, JWT, OAuth2, y métodos de autorización como @PreAuthorize.",
            null,
            cq("¿Qué es un SecurityFilterChain?",
                "Es la cadena de filtros de Spring Security. Define qué rutas están protegidas, qué roles se requieren, y qué mecanismos de autenticación usar. En Spring Boot 3.x se configura con un bean @Bean SecurityFilterChain filterChain(HttpSecurity http). Reemplaza el antiguo WebSecurityConfigurerAdapter."),
            cq("¿Qué diferencia autenticación de autorización?",
                "Autenticación: verificar quién eres (login, usuario/contraseña, JWT). Autorización: decidir qué puedes hacer (roles, permisos, scopes). Spring Security maneja ambas: AuthenticationManager para autenticación, y DecisionManager/annotations para autorización."),
            cq("¿Qué es JWT?",
                "JSON Web Token: token firmado que contiene claims (identidad, roles, expiración). Se usa en APIs stateless. El cliente envía el token en header Authorization: Bearer <token>. Spring Security valida la firma y extrae la información de autenticación.")
        );
        sc(springSecurity, "Configuración básica", "security-configuracion-basica", 1,
            "Configuración mínima con SecurityFilterChain. Permite acceso público a ciertas rutas y requiere autenticación para el resto. En Spring Boot 3 se usa lambda DSL.",
            """
            @Configuration
            @EnableWebSecurity
            public class SecurityConfig {

                @Bean
                public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                    http
                        .csrf(csrf -> csrf.disable());
                        .authorizeHttpRequests(auth -> auth
                            .requestMatchers("/public/**", "/login").permitAll()
                            .requestMatchers("/admin/**").hasRole("ADMIN")
                            .anyRequest().authenticated()
                        )
                        .httpBasic(Customizer.withDefaults());;
                    return http.build();
                }
            }
            """,
            q("¿Por qué Spring Boot genera una contraseña por defecto?",
                "Cuando añades spring-boot-starter-security sin configurar usuarios, crea un usuario user con una contraseña aleatoria impresa en el log. Es para desarrollo. En producción, configura UserDetailsService o un proveedor de identidad real (LDAP, OAuth2, base de datos)."),
            q("¿Qué es CSRF y por qué desactivarlo en APIs?",
                "Cross-Site Request Forgery: ataque que fuerza a un usuario autenticado a ejecutar acciones no deseadas. En APIs REST stateless con JWT, el cliente no mantiene sesión ni cookies, por lo que CSRF no aplica y se desactiva. En form-based apps tradicionales, CSRF debe estar activado.")
        );
        sc(springSecurity, "UserDetailsService", "userdetails-service", 2,
            "UserDetailsService es la interfaz que Spring Security usa para cargar usuarios. Implementándola puedes leer credenciales de BBDD, LDAP, etc. Devuelve un UserDetails con username, password y authorities.",
            """
            @Service
            public class UsuarioDetailsService implements UserDetailsService {

                private final UsuarioRepository repo;

                public UsuarioDetailsService(UsuarioRepository repo) {
                    this.repo = repo;
                }

                @Override
                public UserDetails loadUserByUsername(String username) {
                    Usuario u = repo.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException(username));;
                    return User.builder()
                        .username(u.getUsername());
                        .password(u.getPassword());
                        .roles(u.getRol());
                        .build();
                }
            }
            """,
            q("¿Password debe estar hasheada?",
                "Sí, absolutamente. Spring Security compara con PasswordEncoder (BCryptPasswordEncoder recomendado). Nunca guardes contraseñas en texto plano. Al crear usuario: passwordEncoder.encode(password). Al autenticar, el AuthenticationManager compara usando el encoder configurado."),
            q("¿Authorities vs Roles?",
                "Roles son authorities con prefijo ROLE_. hasRole('ADMIN') comprueba ROLE_ADMIN. hasAuthority('EDITAR_USUARIO') comprueba la authority exacta. Roles son más genéricos; authorities más granulares. Internamente ambos se almacenan en la colección de GrantedAuthority."),
            q("¿Qué devuelve loadUserByUsername si no encuentra usuario?",
                "Debe lanzar UsernameNotFoundException. Spring Security la captura y falla la autenticación. No devuelvas null: en versiones antiguas era posible, pero ahora lanza excepción. El mensaje debe ser genérico para no dar pistas a atacantes.")
        );
        sc(springSecurity, "Autorización con @PreAuthorize", "preauthorize", 3,
            "@PreAuthorize evalúa una expresión SpEL antes de ejecutar el método. Requiere @EnableMethodSecurity. Permite control de acceso a nivel de método además del control de URLs.",
            """
            @Configuration
            @EnableMethodSecurity
            public class MethodSecurityConfig {}

            @Service
            public class PedidoService {

                @PreAuthorize("hasRole('ADMIN') or #usuarioId == authentication.principal.id")
                public Pedido obtenerPedido(Long usuarioId, Long pedidoId) {
                    return repo.findById(pedidoId);
                }
            }
            """,
            q("¿@PreAuthorize vs @Secured?",
                "@Secured es más antiguo y solo soporta roles simples. @PreAuthorize usa SpEL y permite expresiones complejas (hasRole, hasAuthority, comparaciones con parámetros, etc.). Recomendado usar @PreAuthorize/@PostAuthorize para flexibilidad. Requiere @EnableMethodSecurity."),
            q("¿Qué pasa si @EnableMethodSecurity falta?",
                "Las anotaciones @PreAuthorize, @PostAuthorize, @PreFilter, @PostFilter no se procesan. Los métodos se ejecutan sin control de autorización, creando un agujero de seguridad. Siempre habilita @EnableMethodSecurity cuando uses estas anotaciones.")
        );

        // ===== SPRING BOOT =====
        Concept springBoot = concept("Spring Boot Fundamentos", "spring-boot-fundamentos", Block.SPRING_BOOT, 1,
            "Spring Boot es un framework que simplifica el desarrollo de aplicaciones Spring. Proporciona auto-configuración, starters de dependencias y un servidor embebido. Permite crear aplicaciones standalone listas para producción con mínima configuración. Elimina gran parte del boilerplate XML y reduce el tiempo de setup.",
            null,
            cq("¿Qué es Spring Boot?",
                "Es una extensión del ecosistema Spring que facilita crear aplicaciones Spring autónomas y listas para producción. Ofrece: auto-configuración, opinionated defaults, servidores embebidos (Tomcat/Jetty/Undertow), y Spring Initializr para generar proyectos rápidamente. Reduce drásticamente la configuración necesaria."),
            cq("¿Qué son los Spring Boot Starters?",
                "Son dependencias Maven/Gradle preconfiguradas que agrupan librerías relacionadas. Ejemplos: spring-boot-starter-web (web MVC), spring-boot-starter-data-jpa (JPA), spring-boot-starter-test (testing). Cada starter trae las versiones compatibles de sus dependencias (BOM), resolviendo conflictos. Simplifican el pom.xml."),
            cq("¿Qué es el auto-configuración de Spring Boot?",
                "Mecanismo que configura automáticamente beans basándose en las dependencias del classpath y las propiedades. Si detecta spring-webmvc en classpath, configura un dispatcher servlet. Si detecta H2, configura datasource. Puedes personalizar o deshabilitar con @EnableAutoConfiguration(exclude=...) o spring.autoconfigure.exclude.")
        );
        sc(springBoot, "SpringApplication", "spring-application", 1,
            "La clase main anotada con @SpringBootApplication lanza SpringApplication.run(). Esto arranca el ApplicationContext, carga el banner, inicia el servidor embebido y ejecuta CommandLineRunner/ApplicationRunner beans.",
            """
            @SpringBootApplication
            public class MiApp {
                public static void main(String[] args) {
                    SpringApplication.run(MiApp.class, args);
                }
            }

            // O personalizar
            SpringApplication app = new SpringApplication(MiApp.class);
            app.setBannerMode(Banner.Mode.OFF);
            app.run(args);
            """,
            q("¿Qué hace @SpringBootApplication?",
                "Es una combinación de tres anotaciones: @Configuration (marca la clase como fuente de beans), @EnableAutoConfiguration (activa auto-configuración), y @ComponentScan (escanea componentes en el paquete actual y subpaquetes). Equivale a poner las tres juntas."),
            q("¿Diferencia CommandLineRunner vs ApplicationRunner?",
                "Ambos ejecutan código después de que arranca el contexto. CommandLineRunner recibe String... args (crudo de la línea de comandos). ApplicationRunner recibe ApplicationArguments que parsea options (--foo=bar) y non-option args por separado. Usa ApplicationRunner para lógica más limpia con argumentos.")
        );
        sc(springBoot, "application.properties", "application-properties", 2,
            "Fichero de configuración externalizada. Soporta perfiles (application-dev.properties), placeholders (${VAR}), y tipos de datos. Spring Boot carga automáticamente propiedades por orden de precedencia: línea de comandos > variables entorno > application-profile > application.",
            """
            # application.properties
            server.port=8081
            spring.application.name=mi-app
            logging.level.org.springframework=INFO

            # application-dev.properties (perfil dev)
            spring.datasource.url=jdbc:h2:mem:testdb
            """,
            q("¿Cómo se activa un perfil?",
                "Vía propiedad spring.profiles.active=dev (application.properties), variable de entorno SPRING_PROFILES_ACTIVE=dev, o argumento JVM -Dspring.profiles.active=dev. También programáticamente con SpringApplication.setAdditionalProfiles(). Los beans @Profile se activan según el perfil activo."),
            q("¿Propiedades en YAML?",
                "application.yml usa indentación y soporta listas y maps. Útil para configuraciones jerárquicas. Equivalente a application.properties. Puedes combinar ambos; properties tienen prioridad sobre yml si hay conflicto en la misma ubicación según orden de carga.")
        );
        sc(springBoot, "Spring Boot Actuator", "spring-boot-actuator", 3,
            "Actuator expone endpoints de producción: health, info, metrics, env, loggers, etc. Se activa con spring-boot-starter-actuator. /actuator/health es el más común; devuelve UP/DOWN. Requiere configurar seguridad para no exponer endpoints sensibles.",
            """
            management.endpoints.web.exposure.include=health,info,metrics
            management.endpoint.health.show-details=always
            info.app.name=Mi Aplicacion
            info.app.version=1.0.0
            """,
            q("¿Es seguro exponer todos los endpoints de Actuator?",
                "No. Endpoints como /env, /heapdump, /threaddump pueden filtrar información sensible. Exponer health e info es seguro; endpoints sensibles deben estar protegidos por autenticación o firewall. En producción, usa management.endpoints.web.exposure.include con lista blanca."),
            q("¿Para qué sirve /actuator/health?",
                "Verifica el estado de la aplicación y sus dependencias (DB, disco, etc.). Devuelve UP si todo OK, DOWN si algún health indicator falla. Es el endpoint estándar para balanceadores y plataformas de orquestación (Kubernetes, Cloud Foundry) para determinar si la app está viva.")
        );
        sc(springBoot, "Spring vs Spring Boot", "spring-vs-spring-boot", 4,
            "Spring es el framework base (IoC, DI, AOP, MVC, Data, Security). Spring Boot es una extensión opiniada que simplifica el uso de Spring: auto-configuración, starters, servidor embebido y configuración por defecto. Spring requiere mucha configuración manual; Spring Boot reduce drásticamente el boilerplate.",
            """
            // Spring tradicional: mucha configuración XML/Java
            // web.xml, dispatcher-servlet.xml, applicationContext.xml...

            // Spring Boot: una clase main
            @SpringBootApplication
            public class MiApp {
                public static void main(String[] args) {
                    SpringApplication.run(MiApp.class, args);
                }
            }
            """,
            q("¿Cuál es la diferencia principal entre Spring y Spring Boot?",
                "Spring es un framework completo para desarrollo empresarial Java (DI, MVC, Security, Data...). Spring Boot es una capa sobre Spring que facilita la creación de apps autónomas: auto-configuración, starters, servidor embebido y configuración por convención. Spring Boot no reemplaza Spring, lo hace más productivo."),
            q("¿Se puede usar Spring sin Spring Boot?",
                "Sí, pero requiere configuración manual extensa: definir el ApplicationContext, configurar el dispatcher servlet, gestionar dependencias, desplegar en un servidor externo (Tomcat/ Jetty). Spring Boot automatiza todo eso y permite crear JAR ejecutable con servidor embebido."),
            q("¿Qué ventajas da Spring Boot sobre Spring puro?",
                "1) Auto-configuración basada en classpath. 2) Starters que agrupan dependencias compatibles. 3) Servidor embebido (sin desplegar WAR). 4) Configuración externalizada y perfiles. 5) Spring Initializr para scaffolding. 6) Actuator para monitoreo. 7) Menos código de configuración y más rápido time-to-market.")
        );

        // ===== JPA / HIBERNATE =====
        Concept jpa = concept("JPA", "jpa", Block.JPA_HIBERNATE, 1,
            "Java Persistence API es la especificación estándar de Java para mapeo objeto-relacional (ORM). Define entidades, EntityManager, JPQL, transacciones y criterios de consulta. Implementaciones populares: Hibernate, EclipseLink, OpenJPA. Spring Data JPA facilita su uso.",
            null,
            cq("¿Qué es JPA?",
                "Java Persistence API: especificación que define cómo mapear objetos Java a tablas relacionales y cómo gestionar su persistencia. No es una implementación; necesitas un proveedor como Hibernate. Sus interfaces principales son EntityManager y EntityTransaction."),
            cq("¿Diferencia entre JPA e Hibernate?",
                "JPA es la especificación/API. Hibernate es una implementación de JPA (la más usada) que añade funcionalidades propias como HQL, Criteria API, caching de segundo nivel e interceptores. Puedes programar contra JPA y cambiar de proveedor, aunque en la práctica se usan extensiones de Hibernate."),
            cq("¿Qué es una Entity en JPA?",
                "Una clase Java anotada con @Entity que se mapea a una tabla de la base de datos. Debe tener una clave primaria (@Id) y un constructor sin argumentos. Sus atributos se mapean a columnas con @Column, @ManyToOne, @OneToMany, etc."));
        sc(jpa, "Entity Mapping", "entity-mapping", 1,
            "JPA mapea clases Java a tablas mediante anotaciones. @Entity marca la clase, @Table personaliza el nombre, @Id indica la clave primaria, @GeneratedValue genera valores, @Column configura columnas y las anotaciones de relación definen asociaciones.",
            """
            @Entity
            @Table(name = "clientes")
            public class Cliente {
                @Id
                @GeneratedValue(strategy = GenerationType.IDENTITY)
                private Long id;

                @Column(nullable = false, length = 100)
                private String nombre;

                @ManyToOne(fetch = FetchType.LAZY)
                @JoinColumn(name = "ciudad_id")
                private Ciudad ciudad;

                // getter, setter, constructor vacío
            }
            """,
            q("¿Para qué sirve @GeneratedValue?",
                "Indica cómo generar valores para la clave primaria. Estrategias: AUTO (elige el proveedor), IDENTITY (autoincremental de BD), SEQUENCE (secuencia de BD), TABLE (tabla de secuencias). IDENTITY es común en MySQL/PostgreSQL; SEQUENCE en Oracle/PostgreSQL."),
            q("¿Qué relaciones soporta JPA?",
                "@OneToOne, @OneToMany, @ManyToOne y @ManyToMany. @ManyToOne es la relación 'muchos a uno' más común (un Cliente tiene una Ciudad). @OneToMany es su inversa. Siempre define fetch (LAZY recomendado) y mappedBy en el lado inverso."));
        sc(jpa, "JpaRepository", "jpa-repository", 2,
            "Spring Data JPA proporciona JpaRepository<T, ID>, una interfaz que hereda CRUD, paginación y ordenación. No requiere implementación: Spring genera el bean en tiempo de ejecución. Soporta derivación de queries y @Query.",
            """
            public interface PedidoRepository extends JpaRepository<Pedido, Long> {
                List<Pedido> findByEstadoAndFechaGreaterThan(String estado, LocalDate fecha);

                @Query("SELECT p FROM Pedido p JOIN FETCH p.cliente WHERE p.estado = :estado")
                List<Pedido> findConCliente(@Param("estado") String estado);
            }
            """,
            q("¿Qué ventajas tiene JpaRepository sobre EntityManager?",
                "Reduce boilerplate: obtienes save, findById, findAll, delete, exists, count sin implementar nada. Además ofrece paginación, ordenación y derivación de queries. Para casos complejos puedes usar @Query o EntityManager directamente."),
            q("¿Qué es JOIN FETCH?",
                "Indica a JPA que cargue en la misma consulta una relación que sería lazy. Evita el problema N+1 cuando accedes a la relación después de cerrar la consulta. Úsalo cuando necesites la relación en el resultado final."));
        sc(jpa, "JPQL", "jpql", 3,
            "Java Persistence Query Language es un lenguaje orientado a objetos similar a SQL pero que consulta entidades en lugar de tablas. Soporta parámetros con nombre (:param) y posicionales (?1).",
            """
            String jpql = "SELECT c FROM Cliente c WHERE c.activo = :activo ORDER BY c.nombre";
            TypedQuery<Cliente> query = em.createQuery(jpql, Cliente.class);
            query.setParameter("activo", true);
            List<Cliente> clientes = query.getResultList();
            """,
            q("¿JPQL vs SQL?",
                "JPQL opera sobre entidades y atributos Java; SQL sobre tablas y columnas. JPQL es portable entre bases de datos. Las consultas nativas (@Query(nativeQuery=true)); usan SQL directamente y pierden portabilidad."),
            q("¿Cómo evitar SQL Injection en JPQL?",
                "Usa siempre parámetros con nombre (:param) o posicionales (?1). Nunca concatenes valores directamente en la query. Spring Data JPA y EntityManager escapan automáticamente los parámetros."));
        sc(jpa, "Lazy vs Eager", "lazy-vs-eager", 4,
            "FetchType define cuándo cargar relaciones. LAZY carga la relación solo cuando se accede (por defecto en @OneToMany y @ManyToMany). EAGER carga la relación en la consulta principal (por defecto en @ManyToOne y @OneToOne).",
            """
            @Entity
            public class Pedido {
                @ManyToOne(fetch = FetchType.LAZY)  // recomendado
                private Cliente cliente;

                @OneToMany(mappedBy = "pedido", fetch = FetchType.LAZY)
                private List<LineaPedido> lineas;
            }
            """,
            q("¿Qué es el problema N+1?",
                "Ocurre cuando cargas N entidades y luego accedes a una relación lazy para cada una, generando N consultas adicionales. Soluciones: JOIN FETCH, EntityGraph o cambiar a EAGER si la relación siempre se usa."),
            q("¿Por qué preferir LAZY?",
                "Evita cargar datos que no necesitas. Mejora el rendimiento de consultas que no usan la relación. EAGER puede traer árboles enteros de objetos sin querer y causar problemas de rendimiento."));
        sc(jpa, "Transacciones", "transacciones-jpa", 5,
            "JPA requiere una transacción para operaciones de escritura. Spring gestiona transacciones con @Transactional. Propagación define cómo se comportan las transacciones anidadas; isolation define el nivel de aislamiento.",
            """
            @Service
            public class PedidoService {
                @Transactional
                public Pedido crearPedido(PedidoDTO dto) {
                    // guarda cliente, pedido y líneas en una sola transacción
                }
            }
            """,
            q("¿Qué hace @Transactional?",
                "Crea una transacción alrededor del método. Si todo va bien, hace commit; si lanza RuntimeException, hace rollback. También gestiona el EntityManager y el flush. Se puede poner a nivel de clase o método."),
            q("¿Qué es la propagación REQUIRED?",
                "Valor por defecto. Si ya hay una transacción, la reutiliza; si no, crea una nueva. Otros valores: REQUIRES_NEW (siempre nueva), NESTED (savepoint), SUPPORTS, NOT_SUPPORTED, NEVER, MANDATORY."));

        Concept hibernate = concept("Hibernate", "hibernate", Block.JPA_HIBERNATE, 2,
            "Hibernate es la implementación más popular de JPA. Añade características propias como HQL, Criteria API, caching de segundo nivel, interceptores y conversores de tipos. Es la opción por defecto en Spring Boot con spring-boot-starter-data-jpa.",
            null,
            cq("¿Por qué Hibernate sobre JPA puro?",
                "Hibernate es más maduro, tiene mejor documentación, caching avanzado, HQL, Criteria API y es el motor por defecto de Spring Boot. La mayoría de proyectos Spring usan Hibernate como proveedor JPA sin notarlo."),
            cq("¿Qué es Session en Hibernate?",
                "Session es la API nativa de Hibernate equivalente a EntityManager de JPA. Gestiona el ciclo de vida de las entidades y actúa como caché de primer nivel. Se obtiene de SessionFactory."),
            cq("¿Qué es el caching en Hibernate?",
                "Primer nivel: caché asociada a la Session/EntityManager (obligatoria). Segundo nivel: caché compartida entre sesiones (opcional, requiere proveedor como Ehcache). Query cache: cachea resultados de consultas."));
        sc(hibernate, "Session / EntityManager", "session-entitymanager", 1,
            "EntityManager es la API estándar JPA; Session es la API nativa de Hibernate. Ambos gestionan entidades persistentes y actúan como caché de primer nivel. Métodos clave: persist, merge, remove, find, createQuery.",
            """
            @PersistenceContext
            private EntityManager em;

            public Cliente guardar(Cliente c) {
                em.persist(c);   // nuevo → persistente
                return c;
            }

            public Cliente actualizar(Cliente c) {
                return em.merge(c);  // detached → persistente
            }
            """,
            q("¿Diferencia persist y merge?",
                "persist convierte una entidad transient en persistente (insert al commit). merge copia el estado de una entidad detached a una instancia persistente (update o insert). merge devuelve la instancia gestionada; la original sigue detached."),
            q("¿Qué es flush?",
                "Fuerza a Hibernate a sincronizar el contexto de persistencia con la base de datos. Las operaciones pendientes se ejecutan, pero la transacción sigue abierta. Se hace automáticamente al commit o antes de algunas queries."));
        sc(hibernate, "HQL", "hql", 2,
            "Hibernate Query Language es similar a JPQL pero con funcionalidades adicionales de Hibernate. Opera sobre entidades y propiedades Java. Soporta funciones específicas del dialecto de base de datos.",
            """
            String hql = "FROM Cliente c WHERE c.ciudad.nombre = :nombre";
            List<Cliente> clientes = session.createQuery(hql, Cliente.class)
                .setParameter("nombre", "Madrid")
                .getResultList();
            """,
            q("¿HQL vs JPQL?",
                "JPQL es estándar JPA; HQL es propio de Hibernate. HQL es un superconjunto de JPQL: acepta funciones del dialecto SQL, cláusulas específicas y operaciones que JPQL no soporta. En Spring Data JPA se usa JPQL en @Query."),
            q("¿HQL vs SQL nativo?",
                "HQL es portable entre bases de datos y orientado a objetos. SQL nativo usa el dialecto específico de la BD y devuelve Object[] o se mapea con @SqlResultSetMapping. Usa SQL nativo solo cuando necesites funciones específicas de la BD."));
        sc(hibernate, "Criteria API", "criteria-api", 3,
            "Criteria API permite construir queries de forma programática y tipada. Útil para queries dinámicas donde se añaden predicados condicionalmente. Hibernate soporta JPA Criteria y su Criteria API nativa.",
            """
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Cliente> cq = cb.createQuery(Cliente.class);
            Root<Cliente> root = cq.from(Cliente.class);

            List<Predicate> predicates = new ArrayList<>();
            if (nombre != null) {
                predicates.add(cb.like(root.get("nombre"), "%" + nombre + "%"));;
            }
            if (activo != null) {
                predicates.add(cb.equal(root.get("activo"), activo));;
            }

            cq.where(predicates.toArray(new Predicate[0]));;
            List<Cliente> clientes = em.createQuery(cq).getResultList();
            """,
            q("¿Ventajas de Criteria API?",
                "Type-safe: se comprueba en compilación. Ideal para queries dinámicas donde los filtros dependen de parámetros de entrada. Evita concatenación de strings y SQL Injection."),
            q("¿Cuándo usar Criteria en lugar de JPQL?",
                "Cuando la query se construye dinámicamente según filtros opcionales. Si la query es estática, JPQL o @Query son más legibles y concisas."));
        sc(hibernate, "Caching", "hibernate-caching", 4,
            "Hibernate tiene tres niveles de caché: primer nivel (Session/EntityManager), segundo nivel (compartido entre sesiones) y caché de consultas. El segundo nivel requiere configurar un proveedor como Ehcache o Caffeine.",
            """
            # application.properties
            spring.jpa.properties.hibernate.cache.use_second_level_cache=true
            spring.jpa.properties.hibernate.cache.region.factory_class=org.hibernate.cache.jcache.JCacheRegionFactory
            spring.jpa.properties.javax.persistence.sharedCache.mode=ENABLE_SELECTIVE

            @Entity
            @Cacheable
            @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
            public class Cliente { ... }
            """,
            q("¿Qué se guarda en el primer nivel?",
                "Entidades gestionadas por la Session/EntityManager activa. Se invalida al cerrar la sesión. Evita consultas repetidas dentro de la misma transacción cuando haces findById varias veces."),
            q("¿Cuándo usar segundo nivel de caché?",
                "Para entidades de lectura frecuente que cambian poco (catálogos, configuraciones). Reduce consultas a BD entre transacciones distintas. No es mágico: requiere análisis y puede añadir complejidad."));
        sc(hibernate, "Entity Lifecycle", "entity-lifecycle", 5,
            "Las entidades pasan por estados: transient (no gestionada), managed/persistent (gestionada por el EntityManager), detached (tiene ID pero no está gestionada) y removed (marcada para borrado).",
            """
            Cliente c = new Cliente();          // transient
            em.persist(c);                       // persistent
            em.detach(c);                        // detached
            Cliente merged = em.merge(c);        // persistent de nuevo
            em.remove(merged);                   // removed (borrado al commit)
            """,
            q("¿Qué es una entidad detached?",
                "Tiene identidad (ID asignado) pero no está asociada a un EntityManager activo. Los cambios no se sincronizan automáticamente. Para reasociarla, usa merge. Es común en APIs REST donde el objeto viaja al cliente y vuelve."),
            q("¿Qué pasa con removed al hacer commit?",
                "Hibernate ejecuta DELETE en la base de datos. Si haces rollback, la entidad vuelve a estar detached. No puedes usar persist sobre una entidad removed; necesitas crear una nueva instancia."));

        // Guardar/actualizar conceptos raíz de forma idempotente
        List<Concept> allRoots = List.of(
            clase, objeto, herencia, polimorfismo, encapsulamiento,
            interfaceConcept, abstractClass, excepciones, genericos,
            colecciones, streams,
            plataformaJava,
            springFramework, ioc, springMvc, springSecurity,
            springBoot,
            jpa, hibernate
        );
        for (Concept root : allRoots) {
            saveOrMergeRoot(root);
        }
    }

    private void saveOrMergeRoot(Concept newRoot) {
        Optional<Concept> existingOpt = conceptRepository.findBySlug(newRoot.getSlug());
        if (existingOpt.isEmpty()) {
            conceptRepository.save(newRoot);
            return;
        }

        Concept existing = existingOpt.get();
        existing.setDescription(newRoot.getDescription());
        existing.setCodeExample(newRoot.getCodeExample());
        existing.setBlock(newRoot.getBlock());

        for (ConceptQuestion newQ : newRoot.getQuestions()) {
            boolean exists = existing.getQuestions().stream()
                    .anyMatch(q -> q.getQuestion().equals(newQ.getQuestion()));
            if (!exists) {
                existing.addQuestion(new ConceptQuestion(newQ.getQuestion(), newQ.getAnswer()));
            }
        }

        for (SubConcept newSc : newRoot.getSubConcepts()) {
            boolean exists = existing.getSubConcepts().stream()
                    .anyMatch(sc -> sc.getSlug().equals(newSc.getSlug()));
            if (!exists) {
                SubConcept sc = new SubConcept(
                        newSc.getTitle(), newSc.getSlug(), newSc.getOrderIndex(),
                        newSc.getDescription(), newSc.getCodeExample());
                for (SubConceptQuestion newSq : newSc.getQuestions()) {
                    sc.addQuestion(new SubConceptQuestion(newSq.getQuestion(), newSq.getAnswer()));
                }
                existing.addSubConcept(sc);
            }
        }

        conceptRepository.save(existing);
    }

    // Helper methods (sin guardar inmediatamente; se hace batch al final)
    private Concept concept(String title, String slug, Block block, int order) {
        return new Concept(title, slug, block, order);
    }

    private Concept concept(String title, String slug, Block block, int order, String desc, String code, CQ... questions) {
        Concept c = new Concept(title, slug, block, order);
        c.setDescription(desc);
        c.setCodeExample(code);
        for (CQ q : questions) {
            c.addQuestion(new ConceptQuestion(q.question, q.answer));
        }
        return c;
    }

    private void sc(Concept parent, String title, String slug, int order, String desc, String code, Q... questions) {
        SubConcept sc = new SubConcept(title, slug, order, desc, code);
        parent.addSubConcept(sc);
        for (Q q : questions) {
            sc.addQuestion(new SubConceptQuestion(q.question, q.answer));
        }
    }

    private Q q(String question, String answer) {
        return new Q(question, answer);
    }

    private CQ cq(String question, String answer) {
        return new CQ(question, answer);
    }

    private record Q(String question, String answer) {}
    private record CQ(String question, String answer) {}
}
