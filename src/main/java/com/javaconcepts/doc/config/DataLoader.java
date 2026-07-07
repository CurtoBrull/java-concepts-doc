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

import java.util.*;

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

        // ===== CONCURRENCIA =====
        Concept multithreading = concept("Multithreading", "multithreading", Block.JAVA_CORE, 12,
            "Multithreading permite ejecutar múltiples hilos de ejecución simultáneamente dentro de un mismo proceso. En Java se implementa con Thread, Runnable, Callable y ExecutorService. Esencial para aprovechar CPUs multinúcleo y para tareas concurrentes.",
            null,
            cq("¿Qué es un hilo en Java?",
                "Un hilo (thread) es un flujo independiente de ejecución dentro de un proceso. Cada hilo tiene su propia pila de llamadas pero comparte el heap con otros hilos del mismo proceso. Java soporta multithreading nativamente."),
            cq("¿Cómo se crea un hilo?",
                "Dos formas principales: extender Thread o implementar Runnable. La segunda es preferida porque permite heredar de otra clase y separa la tarea del mecanismo de ejecución. Desde Java 5 se recomienda usar ExecutorService en lugar de crear Threads manualmente."),
            cq("¿Runnable vs Callable?",
                "Runnable no devuelve resultado ni lanza checked exceptions. Callable devuelve un Future<T> y puede lanzar excepciones. Usa Callable cuando necesites resultado o manejo de errores desde el hilo.")
        );
        sc(multithreading, "Thread y Runnable", "thread-runnable", 1,
            "Formas básicas de crear hilos. Implementar Runnable es más flexible. Para iniciar un hilo se llama a start(), no run(); run() ejecuta el código en el hilo actual.",
            """
            // Extender Thread
            class MiHilo extends Thread {
                @Override
                public void run() {
                    System.out.println("Hilo: " + Thread.currentThread().getName());
                }
            }

            // Implementar Runnable (preferido)
            Runnable tarea = () -> System.out.println("Hilo: " + Thread.currentThread().getName());
            new Thread(tarea).start();
            """,
            q("¿start() vs run()?",
                "start() lanza un nuevo hilo y ejecuta run() en ese hilo. run() ejecuta el código en el hilo actual como cualquier método. Llamar run() directamente no crea concurrencia."),
            q("¿Qué es Thread.sleep()?",
                "Pausa la ejecución del hilo actual durante un tiempo. Puede lanzar InterruptedException. No libera locks que el hilo tenga. Se usa para simular esperas o reducir uso de CPU."));
        sc(multithreading, "ExecutorService", "executor-service", 2,
            "ExecutorService gestiona un pool de hilos reutilizables. Evita crear y destruir hilos constantemente. Ofrece execute(), submit(), invokeAll() y shutdown().",
            """
            ExecutorService executor = Executors.newFixedThreadPool(4);

            Future<Integer> future = executor.submit(() -> {
                return 42;
            });

            try {
                Integer resultado = future.get(); // bloquea hasta obtener resultado
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

            executor.shutdown();
            """,
            q("¿Qué tipos de pools existen?",
                "newFixedThreadPool(n): n hilos fijos. newCachedThreadPool(): hilos dinámicos. newSingleThreadExecutor(): un solo hilo. newScheduledThreadPool(): tareas programadas. En producción se prefiere crear ThreadPoolExecutor manualmente para control total."),
            q("¿Por qué shutdown() es importante?",
                "Apaga el executor de forma ordenada, esperando a que terminen las tareas pendientes. Sin shutdown, la JVM no termina porque los hilos del pool son no daemon. Usa awaitTermination para esperar."));
        sc(multithreading, "Callable y Future", "callable-future", 3,
            "Callable representa una tarea que devuelve resultado. Future permite obtener ese resultado de forma asíncrona, consultar estado o cancelar la tarea.",
            """
            Callable<String> tarea = () -> {
                Thread.sleep(1000);
                return "Terminado";
            };

            Future<String> future = executor.submit(tarea);
            if (!future.isDone()) {
                System.out.println("Aún trabajando...");
            }
            String resultado = future.get(); // bloquea si no ha terminado
            """,
            q("¿Future.get() bloquea?",
                "Sí, si la tarea no ha terminado. También existe get(timeout, TimeUnit) que espera un tiempo máximo. Para no bloquear, usa CompletableFuture."),
            q("¿Cómo cancelar una tarea?",
                "Con future.cancel(mayInterruptIfRunning). Devuelve false si ya estaba cancelada o terminada. isCancelled() indica si fue cancelada."));

        Concept sincronizacion = concept("Sincronización", "sincronizacion", Block.JAVA_CORE, 13,
            "La sincronización coordina el acceso de múltiples hilos a recursos compartidos. Java ofrece synchronized, Locks explícitos, variables volatile y clases atómicas para evitar race conditions.",
            null,
            cq("¿Qué es una race condition?",
                "Situación donde el resultado depende del orden de ejecución de los hilos. Ocurre cuando varios hilos leen y escriben datos compartidos sin sincronización. Puede causar resultados inconsistentes."),
            cq("¿Qué es synchronized?",
                "Palabra clave que bloquea el acceso a un método o bloque de código para un solo hilo a la vez. Puede usarse en métodos de instancia (lock del objeto), métodos estáticos (lock de la clase) o bloques."),
            cq("¿Qué es volatile?",
                "Garantiza visibilidad de una variable entre hilos: los cambios hechos por un hilo son visibles inmediatamente para otros. No garantiza atomicidad en operaciones compuestas (como i++).")
        );
        sc(sincronizacion, "synchronized", "synchronized", 1,
            "Mecanismo intrínseco de bloqueo de Java. Cuando un hilo entra en un bloque synchronized, adquiere el monitor del objeto. Otros hilos deben esperar hasta que lo libere.",
            """
            public class Contador {
                private int valor = 0;

                public synchronized void incrementar() {
                    valor++;
                }

                public void incrementarBloque() {
                    synchronized (this) {
                        valor++;
                    }
                }
            }
            """,
            q("¿synchronized en método estático?",
                "Usa el monitor de la clase (Class object). Bloquea todos los métodos synchronized estáticos de esa clase, no los de instancia."),
            q("¿Desventajas de synchronized?",
                "No se puede interrumpir ni intentar adquirir con timeout. No permite múltiples condiciones. Para mayor flexibilidad se usan ReentrantLock o semáforos."));
        sc(sincronizacion, "ReentrantLock y Locks", "locks", 2,
            "Lock es una interfaz que ofrece bloqueos explícitos con más control que synchronized: tryLock con timeout, bloqueos interrumpibles y múltiples condiciones.",
            """
            private final ReentrantLock lock = new ReentrantLock();

            public void operacion() {
                lock.lock();
                try {
                    // sección crítica
                } finally {
                    lock.unlock();
                }
            }

            // Intentar adquirir con timeout
            if (lock.tryLock(2, TimeUnit.SECONDS)) {
                try { ... } finally { lock.unlock(); }
            }
            """,
            q("¿Por qué unlock() en finally?",
                "Para garantizar que el lock se libera incluso si ocurre una excepción. Si olvidas unlock, causas deadlock."),
            q("¿ReadWriteLock?",
                "Permite múltiples lectores simultáneos pero solo un escritor. Útil cuando lecturas son mucho más frecuentas que escrituras. ReentrantReadWriteLock es la implementación."));
        sc(sincronizacion, "Atomic classes", "atomic-classes", 3,
            "Clases como AtomicInteger, AtomicLong y AtomicReference proporcionan operaciones atómicas sin bloqueos explícitos. Usan CAS (Compare-And-Swap) a nivel hardware.",
            """
            AtomicInteger contador = new AtomicInteger(0);

            contador.incrementAndGet(); // atómico
            contador.addAndGet(5);      // atómico
            contador.compareAndSet(0, 1); // CAS
            """,
            q("¿Atomic vs synchronized?",
                "Atomic es más ligero para operaciones simples en una variable. synchronized es más general y sirve para bloques de código complejos. Para contadores simples, AtomicInteger es preferido."),
            q("¿Qué es CAS?",
                "Compare-And-Swap: operación atómica que compara el valor actual con un esperado y, si coinciden, lo actualiza. Es la base de las clases atómicas y de ConcurrentHashMap."));
        sc(sincronizacion, "Deadlock y Livelock", "deadlock", 4,
            "Deadlock ocurre cuando dos o más hilos se bloquean mutuamente esperando recursos que el otro tiene. Livelock es similar pero los hilos siguen activos sin progresar.",
            """
            // Ejemplo clásico de deadlock con dos locks
            synchronized (lockA) {
                synchronized (lockB) { // otro hilo tiene lockB y espera lockA
                    // ...
                }
            }
            """,
            q("¿Cómo evitar deadlock?",
                "Adquirir locks siempre en el mismo orden. Usar tryLock con timeout. Minimizar secciones críticas. Evitar locks anidados cuando sea posible."),
            q("¿Cómo detectar deadlock?",
                "Con herramientas como jstack, JConsole o VisualVM. Java puede detectar deadlocks cíclicos y mostrarlos en thread dumps."));

        Concept concurrenciaAvanzada = concept("Concurrencia Avanzada", "concurrencia-avanzada", Block.JAVA_CORE, 14,
            "Java proporciona utilidades concurrentes de alto nivel: colecciones thread-safe, colas bloqueantes y CompletableFuture para composición de tareas asíncronas.",
            null,
            cq("¿Qué es CompletableFuture?",
                "Clase para programación asíncrona y reactiva en Java. Permite encadenar tareas con thenApply, thenCompose, thenCombine, handle, exceptionally. Evita bloqueos con get() y callbacks."),
            cq("¿ConcurrentHashMap vs HashMap sincronizado?",
                "ConcurrentHashMap permite concurrencia de lecturas y un número limitado de escrituras simultáneas. Es más escalable que Collections.synchronizedMap() que bloquea todo el mapa."),
            cq("¿Qué es una BlockingQueue?",
                "Cola thread-safe que bloquea al productor cuando está llena y al consumidor cuando está vacía. Ideal para patrón productor-consumidor. Implementaciones: ArrayBlockingQueue, LinkedBlockingQueue, PriorityBlockingQueue.")
        );
        sc(concurrenciaAvanzada, "Concurrent Collections", "concurrent-collections", 1,
            "Colecciones diseñadas para uso concurrente sin bloqueos externos. ConcurrentHashMap, CopyOnWriteArrayList, ConcurrentLinkedQueue son las más usadas.",
            """
            Map<String, Integer> mapa = new ConcurrentHashMap<>();
            mapa.put("a", 1);
            mapa.computeIfAbsent("b", k -> 2);

            List<String> lista = new CopyOnWriteArrayList<>();
            // Útil cuando hay muchas lecturas y pocas escrituras
            """,
            q("¿Cuándo usar CopyOnWriteArrayList?",
                "Cuando las lecturas son mucho más frecuentes que las escrituras. Cada escritura crea una copia completa de la lista, por lo que escrituras frecuentes son costosas."),
            q("¿ConcurrentHashMap permite null?",
                "No, ni claves ni valores null. Si necesitas null, usa Collections.synchronizedMap(new HashMap<>()) o Wrap con Optional."));
        sc(concurrenciaAvanzada, "BlockingQueue", "blocking-queue", 2,
            "Cola thread-safe con operaciones bloqueantes. Implementa el patrón productor-consumidor de forma elegante y segura.",
            """
            BlockingQueue<String> cola = new LinkedBlockingQueue<>(10);

            // Productor
            cola.put("tarea"); // bloquea si la cola está llena

            // Consumidor
            String tarea = cola.take(); // bloquea si la cola está vacía
            """,
            q("¿Diferencia put/offer y take/poll?",
                "put/take bloquean indefinidamente. offer/poll no bloquean o aceptan timeout. Usa las bloqueantes cuando el consumidor/productor debe esperar."),
            q("¿ArrayBlockingQueue vs LinkedBlockingQueue?",
                "ArrayBlockingQueue tiene capacidad fija y usa un array circular. LinkedBlockingQueue puede ser bounded o unbounded y usa nodos enlazados. Array es más compacto; Linked es más flexible."));
        sc(concurrenciaAvanzada, "CompletableFuture", "completable-future", 3,
            "Permite componer operaciones asíncronas de forma declarativa. Sustituye la complejidad de callbacks anidados y mejora la legibilidad frente a Future básico.",
            """
            CompletableFuture.supplyAsync(() -> obtenerUsuario(id))
                .thenApply(usuario -> usuario.getEmail())
                .thenCompose(email -> enviarEmailAsync(email))
                .exceptionally(ex -> {
                    log.error("Error: ", ex);
                    return "fallback";
                });
            """,
            q("¿thenApply vs thenCompose?",
                "thenApply transforma el resultado de forma síncrona. thenCompose aplana un CompletableFuture devuelto por la función (evita Future<Future<T>>)."),
            q("¿ supplyAsync usa ForkJoinPool?",
                "Sí, por defecto usa ForkJoinPool.commonPool(). Puedes pasar tu propio Executor para controlar el pool de hilos."));

        // ===== PLATAFORMA JAVA =====
        Concept plataformaJava = concept("Plataforma Java", "plataforma-java", Block.JAVA_CORE, 15,
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

        // ===== TESTING =====
        Concept testing = concept("Testing", "testing", Block.JAVA_CORE, 16,
            "El testing automatizado verifica que el código funciona correctamente y sigue funcionando tras cambios. En Java el estándar es JUnit 5 junto con Mockito para mocks y AssertJ para assertions expresivas.",
            null,
            cq("¿Qué es JUnit?",
                "Framework de testing unitario para Java. JUnit 5 (Jupiter) es la versión actual y ofrece anotaciones como @Test, @BeforeEach, @AfterEach, @ParameterizedTest, @Nested. Es el estándar de facto en proyectos Java."),
            cq("¿Qué es Mockito?",
                "Framework para crear mocks (objetos simulados) en tests. Permite simular dependencias externas (BBDD, servicios, APIs) y verificar interacciones. Anotaciones comunes: @Mock, @InjectMocks, @Spy."),
            cq("¿AssertJ vs JUnit assertions?",
                "AssertJ ofrece assertions fluent y legibles: assertThat(lista).hasSize(2).contains('a');. JUnit assertions son más básicas. AssertJ mejora mucho la legibilidad de los tests y los mensajes de error.")
        );
        sc(testing, "JUnit 5", "junit-5", 1,
            "JUnit 5 (Jupiter) es la plataforma de testing moderna. Soporta tests anidados, parametrizados, de excepciones, extensiones y ciclo de vida claro.",
            """
            @SpringBootTest
            class CalculadoraTest {

                @BeforeEach
                void setUp() {
                    calculadora = new Calculadora();
                }

                @Test
                void debeSumarDosNumeros() {
                    int resultado = calculadora.sumar(2, 3);
                    assertEquals(5, resultado);
                }

                @Test
                void debeLanzarExcepcionConDivisorCero() {
                    assertThrows(ArithmeticException.class, () -> calculadora.dividir(1, 0));
                }
            }
            """,
            q("¿@BeforeEach vs @BeforeAll?",
                "@BeforeEach se ejecuta antes de cada test. @BeforeAll se ejecuta una sola vez antes de todos los tests de la clase y debe ser estático. Equivalentes a @AfterEach y @AfterAll para limpieza."),
            q("¿Qué son los tests parametrizados?",
                "Permiten ejecutar el mismo test con diferentes datos. Usan @ParameterizedTest con @ValueSource, @CsvSource o @MethodSource. Reducen duplicación y aumentan cobertura."));
        sc(testing, "Mockito", "mockito", 2,
            "Mockito crea objetos simulados para aislar la clase bajo test. Permite definir comportamiento con when/thenReturn y verificar interacciones con verify.",
            """
            @ExtendWith(MockitoExtension.class)
            class PedidoServiceTest {

                @Mock
                private PedidoRepository repository;

                @InjectMocks
                private PedidoService service;

                @Test
                void debeCrearPedido() {
                    when(repository.save(any(Pedido.class)))
                        .thenReturn(new Pedido(1L));

                    Pedido resultado = service.crear(new PedidoDTO());

                    assertNotNull(resultado.getId());
                    verify(repository, times(1)).save(any(Pedido.class));
                }
            }
            """,
            q("¿@Mock vs @Spy?",
                "@Mock crea un objeto simulado completamente. @Spy envuelve una instancia real: por defecto llama a los métodos reales, pero puedes stubbear algunos."),
            q("¿verify qué hace?",
                "Comprueba que se llamó a un método del mock con los argumentos esperados. Ejemplos: verify(repo).save(pedido); verify(repo, never()).delete(any()); verify(repo, times(2)).findById(anyLong())."));
        sc(testing, "AssertJ", "assertj", 3,
            "Librería de assertions fluent que mejora la legibilidad de los tests. Ofrece mensajes de error claros y soporta colecciones, Optional, excepciones y más.",
            """
            import static org.assertj.core.api.Assertions.assertThat;
            import static org.assertj.core.api.Assertions.assertThatThrownBy;

            assertThat(usuario.getNombre()).isEqualTo("Ana");
            assertThat(lista).hasSize(3).contains("a", "b").doesNotContain("z");

            assertThatThrownBy(() -> service.procesar(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("no puede ser null");
            """,
            q("¿Ventajas de AssertJ sobre assertEquals?",
                "Mensajes de error más descriptivos, API fluida y descubrible, mejor manejo de colecciones y tipos complejos. El orden de parámetros es más intuitivo (actual primero)."),
            q("¿AssertJ funciona con JUnit 5?",
                "Sí, es totalmente compatible. Puedes mezclar assertions de JUnit y AssertJ en el mismo test. Muchos proyectos usan AssertJ exclusivamente para assertions."));
        sc(testing, "Tests de integración", "tests-integracion", 4,
            "Los tests de integración verifican que varias capas funcionan juntas: controller + service + repository + base de datos. En Spring se usan @SpringBootTest, @WebMvcTest y @DataJpaTest.",
            """
            @SpringBootTest
            @AutoConfigureMockMvc
            class ClienteControllerIntegrationTest {

                @Autowired
                private MockMvc mockMvc;

                @Autowired
                private ClienteRepository repository;

                @BeforeEach
                void setUp() {
                    repository.deleteAll();
                }

                @Test
                void debeCrearCliente() throws Exception {
                    mockMvc.perform(post("/clientes")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"nombre\":\"Ana\"}"))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.nombre").value("Ana"));
                }
            }
            """,
            q("¿@SpringBootTest vs @WebMvcTest vs @DataJpaTest?",
                "@SpringBootTest carga todo el contexto. @WebMvcTest carga solo capa web con MockMvc. @DataJpaTest carga solo repositorios JPA con base de datos embebida. Usa el más específico para tests más rápidos."),
            q("¿Testcontainers para qué sirve?",
                "Librería que levanta contenedores Docker reales (PostgreSQL, Kafka, Redis) durante los tests. Permite tests de integración fieles al entorno de producción."));
        sc(testing, "TDD", "tdd", 5,
            "Test Driven Development: escribir primero el test, luego el código mínimo para pasarlo, y finalmente refactorizar. Ciclo red-green-refactor.",
            null,
            q("¿Qué es TDD?",
                "Metodología de desarrollo donde escribes un test fallido, implementas el código mínimo para pasarlo y luego refactorizas. Fomenta diseño simple y feedback rápido."),
            q("¿Ventajas de TDD?",
                "Cobertura de tests alta desde el inicio, diseño más desacoplado, menos bugs de regresión y documentación viva del comportamiento esperado."),
            q("¿TDD vs BDD?",
                "TDD se centra en tests técnicos unitarios. BDD (Behavior Driven Development) describe comportamiento en lenguaje cercano al negocio con Given-When-Then. Herramientas: Cucumber, Spock."));
        sc(testing, "Cobertura y calidad", "cobertura", 6,
            "La cobertura de código mide qué porcentaje del código ejecutan los tests, pero no garantiza calidad. Es una métrica, no un objetivo.",
            """
            # Generar reporte con JaCoCo
            mvn test jacoco:report

            # Umbral mínimo en pom.xml
            <execution>
                <goals><goal>check</goal></goals>
                <configuration>
                    <rules>
                        <rule>
                            <limits>
                                <limit>
                                    <counter>LINE</counter>
                                    <value>COVEREDRATIO</value>
                                    <minimum>0.80</minimum>
                                </limit>
                            </limits>
                        </rule>
                    </rules>
                </configuration>
            </execution>
            """,
            q("¿Cobertura del 100% significa que no hay bugs?",
                "No. Indica que todas las líneas se ejecutaron, no que los asserts sean correctos ni que se hayan probado todos los escenarios y edge cases."),
            q("¿JaCoCo?",
                "Java Code Coverage. Herramienta que genera reportes de cobertura. Se integra con Maven/Gradle y CI/CD para fallar builds si no se alcanza el umbral."));

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

        // ===== SPRING BOOT TESTING =====
        Concept springBootTesting = concept("Spring Boot Testing", "spring-boot-testing", Block.SPRING_BOOT, 2,
            "Spring Boot proporciona anotaciones de test que levantan solo las partes del contexto necesarias. Permite probar capas de la aplicación de forma aislada y realista sin depender de un servidor externo.",
            null,
            cq("¿Qué es @SpringBootTest?",
                "Levanta el contexto completo de Spring Boot. Es el test más pesado porque carga toda la aplicación. Úsalo para tests de integración que necesitan múltiples capas (controller + service + repository)."),
            cq("¿Qué es @WebMvcTest?",
                "Levanta solo la capa web (controllers, filters, converters). No carga servicios ni repositorios reales; inyectas mocks con @MockBean. Ideal para tests unitarios de controllers HTTP."),
            cq("¿Qué es @DataJpaTest?",
                "Levanta solo la capa de persistencia JPA. Configura una base de datos en memoria (H2 por defecto), ejecuta @EntityScan y crea los repositories. No carga controllers ni services."));
        sc(springBootTesting, "@SpringBootTest", "springboottest", 1,
            "Test de integración que carga el contexto completo. Útil para probar endpoints reales, flujos completos y configuración de beans.",
            """
            @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
            class MiAppIntegrationTest {

                @LocalServerPort
                private int port;

                @Autowired
                private TestRestTemplate restTemplate;

                @Test
                void healthEndpointDevuelveUp() {
                    String url = "http://localhost:" + port + "/actuator/health";
                    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
                    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
                }
            }
            """,
            q("¿WebEnvironment.RANDOM_PORT para qué sirve?",
                "Evita conflictos de puerto si ejecutas tests en paralelo o si el 8080 está ocupado. Asigna un puerto aleatorio y lo inyecta con @LocalServerPort."),
            q("¿@SpringBootTest es lento?",
                "Sí, porque carga todo el contexto. Para tests rápidos de una sola capa, usa @WebMvcTest, @DataJpaTest o tests unitarios puros con Mockito. Reserva @SpringBootTest para integración."));
        sc(springBootTesting, "@WebMvcTest", "webmvctest", 2,
            "Test aislado de controllers Spring MVC. Carga solo beans web; dependencias se mockean con @MockBean. Usa MockMvc para simular peticiones HTTP.",
            """
            @WebMvcTest(ClienteController.class)
            class ClienteControllerTest {

                @Autowired
                private MockMvc mockMvc;

                @MockBean
                private ClienteService clienteService;

                @Test
                void debeDevolverClientePorId() throws Exception {
                    when(clienteService.obtener(1L))
                        .thenReturn(new ClienteDTO(1L, "Ana"));

                    mockMvc.perform(get("/api/clientes/1"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.nombre").value("Ana"));
                }
            }
            """,
            q("¿MockMvc qué es?",
                "Clase de Spring Test para simular peticiones HTTP y verificar respuestas sin levantar un servidor real. Permite probar controllers de forma rápida y determinista."),
            q("¿@MockBean vs @Mock?",
                "@MockBean crea un mock y lo registra en el contexto de Spring, reemplazando el bean real. @Mock es de Mockito y no interactúa con el contexto de Spring. En @WebMvcTest usa @MockBean."));
        sc(springBootTesting, "@DataJpaTest", "datajpatest", 3,
            "Test de la capa de persistencia. Configura automáticamente H2, JPA y repositories. Permite verificar queries, mappings y transacciones.",
            """
            @DataJpaTest
            class ClienteRepositoryTest {

                @Autowired
                private ClienteRepository repository;

                @Test
                void debeGuardarYRecuperarCliente() {
                    Cliente c = new Cliente(null, "Ana", "ana@mail.com");
                    Cliente guardado = repository.save(c);

                    Optional<Cliente> encontrado = repository.findById(guardado.getId());

                    assertThat(encontrado).isPresent();
                    assertThat(encontrado.get().getNombre()).isEqualTo("Ana");
                }
            }
            """,
            q("¿@DataJpaTest carga toda la app?",
                "No. Carga solo configuración JPA, repositories y una BD en memoria. No levanta controllers ni services. Es mucho más rápido que @SpringBootTest."),
            q("¿Cómo usar BD real con @DataJpaTest?",
                "Puedes deshabilitar el autoconfigurado de H2 con @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) y apuntar a una BD real mediante @TestPropertySource."));
        sc(springBootTesting, "Testcontainers", "testcontainers", 4,
            "Librería que levanta contenedores Docker reales para tests de integración. Permite probar contra PostgreSQL, Redis, Kafka, etc., en lugar de mocks o H2.",
            """
            @Testcontainers
            @SpringBootTest
            class IntegrationTest {

                @Container
                static PostgreSQLContainer<?> postgres =
                    new PostgreSQLContainer<>("postgres:15");

                @DynamicPropertySource
                static void configureProperties(DynamicPropertyRegistry registry) {
                    registry.add("spring.datasource.url", postgres::getJdbcUrl);
                    registry.add("spring.datasource.username", postgres::getUsername);
                    registry.add("spring.datasource.password", postgres::getPassword);
                }
            }
            """,
            q("¿Ventajas de Testcontainers?",
                "Tests contra la misma tecnología que producción (PostgreSQL real vs H2). Detecta problemas de dialecto SQL, constraints y comportamientos específicos de la BD."),
            q("¿Requisitos para Testcontainers?",
                "Necesita Docker instalado y disponible. En CI/CD debe haber soporte para Docker-in-Docker o un runner con Docker. No funciona en entornos sin Docker."));

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

        // ===== CLEAN CODE / SOLID =====
        Concept solid = concept("SOLID", "solid", Block.CLEAN_CODE_SOLID, 1,
            "SOLID es un acrónimo de cinco principios de diseño orientado a objetos que ayudan a crear software mantenible, escalable y fácil de testear. Fueron popularizados por Robert C. Martin (Uncle Bob).",
            null,
            cq("¿Qué es SOLID?",
                "Conjunto de cinco principios de diseño: Single Responsibility, Open/Closed, Liskov Substitution, Interface Segregation y Dependency Inversion. Su objetivo es reducir acoplamiento, aumentar cohesión y facilitar el mantenimiento del código."),
            cq("¿Por qué importa SOLID en entrevistas?",
                "Es una de las preguntas teóricas más comunes en entrevistas Java/Spring. Los revisores suelen pedir ejemplos de cada principio y cómo se aplican en el día a día (inyección de dependencias, interfaces pequeñas, etc.)."),
            cq("¿SOLID aplica solo a POO?",
                "Está pensado para orientación a objetos, pero sus ideas (responsabilidad única, abstracción, inversión de dependencias) son aplicables a otros paradigmas. En Java se ven claramente con clases, interfaces e inyección de dependencias.")
        );
        sc(solid, "Single Responsibility Principle", "single-responsibility", 1,
            "Cada clase debe tener una única razón para cambiar. Si una clase hace demasiadas cosas, se vuelve difícil de mantener, testear y reutilizar.",
            """
            // Mal: una clase que gestiona cliente Y envía emails Y guarda en BBDD
            public class ClienteService {
                public void guardar(Cliente c) { ... }
                public void enviarEmail(Cliente c) { ... }
                public void loggear(Cliente c) { ... }
            }

            // Bien: responsabilidades separadas
            public class ClienteService { ... }
            public class EmailService { ... }
            public class ClienteRepository { ... }
            """,
            q("¿Cómo detectar que violas SRP?",
                "Si una clase tiene múltiples grupos de métodos que cambian por razones distintas, o si el nombre contiene 'And'/'Or', probablemente viola SRP. También si necesitas importar muchas librerías no relacionadas."),
            q("¿SRP vs separación de capas?",
                "SRP es a nivel de clase. La separación en capas (controller, service, repository) es una forma común de aplicarlo, pero dentro de una capa también puedes tener clases con múltiples responsabilidades."));
        sc(solid, "Open/Closed Principle", "open-closed", 2,
            "Las entidades de software deben estar abiertas para extensión pero cerradas para modificación. En Java se logra con interfaces, clases abstractas y polimorfismo.",
            """
            // Extensión sin modificar la lógica existente
            public interface Descuento {
                BigDecimal aplicar(BigDecimal precio);
            }

            public class DescuentoNavidad implements Descuento { ... }
            public class DescuentoEstudiante implements Descuento { ... }

            public class CalculadoraPrecio {
                public BigDecimal calcular(Descuento descuento, BigDecimal precio) {
                    return descuento.aplicar(precio);
                }
            }
            """,
            q("¿OCP significa nunca modificar código?",
                "No. Significa que el comportamiento existente no debería romperse al añadir funcionalidad. A veces es necesario refactorizar, pero el objetivo es minimizar el riesgo de cambios."),
            q("¿Cómo aplica OCP en Spring?",
                "Con inyección de dependencias e interfaces. Puedes añadir nuevas implementaciones de un bean sin tocar el código que lo consume, usando @Primary, @Qualifier o listas de beans."));
        sc(solid, "Liskov Substitution Principle", "liskov-substitution", 3,
            "Las clases hijas deben poder sustituir a sus clases padres sin alterar el comportamiento correcto del programa. Es decir, un subtipo debe ser usable donde se espera el supertipo.",
            """
            // Violación: Rectangle/Square clásica
            public class Rectangulo {
                public void setAncho(int a) { ... }
                public void setAlto(int h) { ... }
            }

            public class Cuadrado extends Rectangulo {
                // Si ancho y alto siempre son iguales, rompe expectativas de Rectangulo
            }
            """,
            q("¿Qué problemas causa violar LSP?",
                "Código que funciona con la clase padre falla con la hija. Check de instancias con instanceof, excepciones inesperadas en overrides y precondiciones más fuertes son señales de violación."),
            q("¿Cómo evitar violar LSP?",
                "Favorecer composición sobre herencia. Si la relación 'es-un' no es perfecta, usa composición. También seguir el contrato del padre: no reforzar precondiciones ni debilitar postcondiciones."));
        sc(solid, "Interface Segregation Principle", "interface-segregation", 4,
            "Es preferible tener muchas interfaces pequeñas y específicas que una interfaz grande y general. Los clientes no deberían depender de métodos que no usan.",
            """
            // Mal: una interfaz gigante
            public interface Trabajador {
                void trabajar();
                void comer();
                void dormir();
                void programar();
            }

            // Bien: interfaces pequeñas
            public interface Trabajador { void trabajar(); }
            public interface Programador { void programar(); }
            public interface Humano { void comer(); void dormir(); }
            """,
            q("¿Relación con ISP en Java?",
                "Java 8 facilita ISP con default methods y static methods en interfaces. Puedes evitar clases abstractas monolíticas y ofrecer contratos pequeños."),
            q("¿Cómo detectar violación de ISP?",
                "Clases que implementan interfaces y dejan métodos vacíos o lanzan UnsupportedOperationException. Significa que la interfaz es demasiado grande para ese cliente."));
        sc(solid, "Dependency Inversion Principle", "dependency-inversion", 5,
            "Los módulos de alto nivel no deben depender de módulos de bajo nivel; ambos deben depender de abstracciones. En Java se implementa con interfaces e inyección de dependencias.",
            """
            // Alto nivel depende de abstracción, no de implementación concreta
            public interface Notificador {
                void enviar(String mensaje);
            }

            public class ServicioPedido {
                private final Notificador notificador;

                public ServicioPedido(Notificador notificador) {
                    this.notificador = notificador;
                }
            }

            // Spring inyecta la implementación concreta
            @Service
            public class EmailNotificador implements Notificador { ... }
            """,
            q("¿DIP vs inyección de dependencias?",
                "DIP es el principio: depender de abstracciones. La inyección de dependencias es una técnica para aplicarlo. Spring usa inyección por constructor para facilitar DIP."),
            q("¿Por qué new viola DIP?",
                "Cuando creas una instancia concretacon new dentro de una clase, el módulo de alto nivel queda acoplado a una implementación. Usar interfaces + inyección desacopla y facilita tests con mocks."));

        Concept cleanCode = concept("Clean Code", "clean-code", Block.CLEAN_CODE_SOLID, 2,
            "Clean Code son prácticas para escribir código legible, mantenible y fácil de entender. No es solo formato: es nombres claros, funciones pequeñas, manejo adecuado de errores y evitar duplicación.",
            null,
            cq("¿Qué es Clean Code?",
                "Código que es fácil de leer, entender y modificar. Prioriza claridad sobre cleverness. Reglas básicas: nombres descriptivos, funciones cortas, una sola responsabilidad, sin comentarios obvios, manejo de errores explícito."),
            cq("¿Cómo nombrar bien variables y métodos?",
                "Usa nombres que revelen intención. Evita abreviaturas crípticas. Métodos deberían ser verbos o frases verbales. Variables booleanas con prefijo is/has/should. Clases con sustantivos."),
            cq("¿Qué es código duplicado?",
                "Tener la misma lógica en varios lugares. SOLucion: extraer a métodos, clases o utilidades compartidas. La regla DRY (Don't Repeat Yourself) promueve eliminar duplicación.")
        );
        sc(cleanCode, "Nombres con significado", "nombres-significativos", 1,
            "Los nombres son la base de la legibilidad. Deben revelar intención, evitar desinformación y ser pronunciables. Un buen nombre reduce la necesidad de comentarios.",
            """
            // Mal
            int d; // días de trabajo
            List<int[]> theList;

            // Bien
            int diasTrabajados;
            List<Celda> celdasDelTablero;

            // Métodos: verbo + contexto
            public int calcularDiasRestantes(LocalDate inicio, LocalDate fin) { ... }
            """,
            q("¿Convenciones de nombres en Java?",
                "Classes y interfaces: PascalCase. Métodos y variables: camelCase. Constantes: SCREAMING_SNAKE_CASE. Paquetes: minúsculas invertidas. Nombres de tests descriptivos: shouldReturnErrorWhenEmailInvalid."),
            q("¿Evitar comentarios con buenos nombres?",
                "Sí. Si necesitas un comentario para explicar qué hace una variable, probablemente el nombre es malo. Los comentarios deben explicar por qué, no qué."));
        sc(cleanCode, "Funciones pequeñas", "funciones-pequenas", 2,
            "Las funciones deben hacer una sola cosa y hacerla bien. Preferir pocas líneas, pocos argumentos y un único nivel de abstracción. Facilitan tests y reutilización.",
            """
            // Mal: función larga y multipropósito
            public void procesarPedido(Pedido p) {
                validar(p);
                calcularTotal(p);
                guardar(p);
                enviarEmail(p);
                actualizarStock(p);
            }

            // Bien: función orquestadora que delega
            public void procesarPedido(Pedido p) {
                validador.validar(p);
                calculador.calcularTotal(p);
                pedidoRepository.save(p);
                notificador.notificar(p);
                stockService.actualizar(p);
            }
            """,
            q("¿Cuántos argumentos debe tener una función?",
                "Idealmente cero, uno o dos. Más de tres obliga a pensar en un objeto parameter. Si hay varios argumentos relacionados, agrúpalos en un DTO o record."),
            q("¿Qué significa 'una sola cosa'?",
                "Una función no debe mezclar niveles de abstracción. No debe a la vez parsear input, validar reglas de negocio y llamar a base de datos. Cada función opera en un nivel."));
        sc(cleanCode, "Manejo de errores", "manejo-errores", 3,
            "El manejo de errores debe ser claro y separado de la lógica de negocio. Usar excepciones en lugar de códigos de error, evitar null como valor de retorno y preferir Optional o excepciones específicas.",
            """
            // Mal: retornar null y obligar a if everywhere
            public Cliente buscar(Long id) {
                return repo.findById(id).orElse(null);
            }

            // Bien: Optional o excepción de dominio
            public Optional<Cliente> buscar(Long id) {
                return repo.findById(id);
            }

            public Cliente buscarOExcepcion(Long id) {
                return repo.findById(id)
                    .orElseThrow(() -> new ClienteNotFoundException(id));
            }
            """,
            q("¿Por qué evitar null?",
                "Null causa NullPointerException. Tony Hoare lo llamó su 'error de mil millones de dólares'. Usa Optional, objetos Null Object o excepciones para indicar ausencia."),
            q("¿Checked vs unchecked exceptions en Clean Code?",
                "Preferir unchecked para errores de programación. Checked exceptions rompen el principio de abrir/cerrar porque todos los métodos intermedios deben declararlas. Usa excepciones de dominio unchecked."));
        sc(cleanCode, "Código duplicado y DRY", "codigo-duplicado", 4,
            "DRY (Don't Repeat Yourself) busca que cada pieza de conocimiento tenga una única representación autoritativa. Duplicar lógica genera inconsistencias cuando hay que cambiarla.",
            """
            // Antes: validación repetida en controller y service
            if (email == null || !email.contains("@")) {
                throw new IllegalArgumentException("Email inválido");
            }

            // Después: validador reutilizable
            @Component
            public class ValidadorEmail {
                public void validar(String email) {
                    if (email == null || !email.contains("@")) {
                        throw new EmailInvalidoException(email);
                    }
                }
            }
            """,
            q("¿DRY vs WET?",
                "WET = Write Everything Twice o We Enjoy Typing. Es lo opuesto a DRY. Un poco de duplicación puede ser aceptable si reduce acoplamiento indebido, pero la regla general es evitarla."),
            q("¿Cómo eliminar duplicación sin crear dependencias raras?",
                "Extraer a clases utilitarias o servicios de dominio compartidos. Si la lógica es de infraestructura, usa utils. Si es de negocio, crea un servicio con responsabilidad clara."));

        // ===== TOOLS =====
        Concept maven = concept("Maven", "maven", Block.TOOLS, 1,
            "Maven es una herramienta de gestión y construcción de proyectos Java. Usa un modelo basado en POM (Project Object Model) para definir dependencias, plugins, ciclo de vida y metas del proyecto.",
            null,
            cq("¿Qué es Maven?",
                "Herramienta de build para Java que automatiza compilación, tests, empaquetado y despliegue. Gestiona dependencias descargándolas de repositorios remotos como Maven Central. Su fichero central es pom.xml."),
            cq("¿Qué es el POM?",
                "Project Object Model: fichero XML que describe el proyecto. Define groupId, artifactId, version, dependencias, plugins, propiedades y perfil. Es el corazón de Maven."),
            cq("¿Qué es Maven Central?",
                "Repositorio remoto público donde se alojan librerías Java. Maven descarga automáticamente dependencias declaradas en pom.xml y las cachea en ~/.m2/repository."));
        sc(maven, "pom.xml básico", "pom-basico", 1,
            "Estructura mínima del descriptor de proyecto Maven. Define identificador, dependencias, plugin de compilación y versión de Java.",
            """
            <?xml version="1.0" encoding="UTF-8"?>
            <project xmlns="http://maven.apache.org/POM/4.0.0">
                <modelVersion>4.0.0</modelVersion>
                <groupId>com.ejemplo</groupId>
                <artifactId>mi-app</artifactId>
                <version>1.0.0</version>
                <packaging>jar</packaging>

                <properties>
                    <maven.compiler.source>21</maven.compiler.source>
                    <maven.compiler.target>21</maven.compiler.target>
                </properties>

                <dependencies>
                    <dependency>
                        <groupId>org.springframework.boot</groupId>
                        <artifactId>spring-boot-starter-web</artifactId>
                        <version>3.3.5</version>
                    </dependency>
                </dependencies>
            </project>
            """,
            q("¿Para qué sirve groupId, artifactId, version?",
                "Identifican únicamente un artefacto Maven. groupId es el namespace de la organización, artifactId el nombre del proyecto, version la versión. Juntos forman las coordenadas GAV."),
            q("¿Qué es packaging?",
                "Indica el formato de salida: jar, war, pom. jar para aplicaciones standalone o librerías. war para aplicaciones web tradicionales desplegadas en servidor externo. pom para proyectos padre/agregadores."));
        sc(maven, "Ciclo de vida y fases", "ciclo-vida-maven", 2,
            "Maven define ciclos de vida con fases ordenadas. Las principales son validate, compile, test, package, verify, install, deploy. Cada fase ejecuta los goals de plugins asociados.",
            """
            # Fases comunes
            mvn clean          # limpia target/
            mvn compile        # compila fuentes
            mvn test           # ejecuta tests
            mvn package        # empaqueta en jar/war
            mvn install        # instala en repositorio local
            mvn deploy         # publica en repositorio remoto

            # Saltar tests
            mvn package -DskipTests
            """,
            q("¿clean es una fase?",
                "No, clean es un ciclo de vida independiente con su propia fase clean. Por eso se ejecuta como mvn clean package: primero limpia, luego ejecuta el ciclo de vida default hasta package."),
            q("¿Fase vs goal?",
                "Fase es una etapa del ciclo de vida (ej: test). Goal es una tarea específica de un plugin (ej: surefire:test). Una fase puede ejecutar varios goals; un goal puede llamarse directamente con mvn plugin:goal."));
        sc(maven, "Dependencias y scopes", "dependencias-maven", 3,
            "Maven gestiona dependencias transitivas. Los scopes definen en qué fases se usan: compile, provided, runtime, test, optional.",
            """
            <dependency>
                <groupId>org.projectlombok</groupId>
                <artifactId>lombok</artifactId>
                <version>1.18.30</version>
                <scope>provided</scope>
            </dependency>

            <dependency>
                <groupId>org.postgresql</groupId>
                <artifactId>postgresql</artifactId>
                <version>42.7.4</version>
                <scope>runtime</scope>
            </dependency>
            """,
            q("¿Qué es una dependencia transitiva?",
                "Si tu proyecto depende de A, y A depende de B, Maven trae B automáticamente. Puedes excluir transitivas con <exclusions> si causan conflictos de versiones."),
            q("¿Scope test vs provided?",
                "test: solo disponible para compilación y ejecución de tests. provided: necesaria para compilar pero proporcionada por el runtime (ej: servlet-api en un Tomcat externo, Lombok). runtime: no necesaria para compilar, sí para ejecutar (ej: driver JDBC)."));

        // ===== DESIGN PATTERNS =====
        Concept designPatterns = concept("Design Patterns", "design-patterns", Block.JAVA_CORE, 17,
            "Los patrones de diseño son soluciones probadas a problemas comunes de diseño de software. No son librerías ni código copiable, sino plantillas que adaptas a tu contexto.",
            null,
            cq("¿Qué son los Design Patterns?",
                "Soluciones reutilizables a problemas recurrentes en diseño de software. Se clasifican en creacionales, estructurales y de comportamiento. Facilitan comunicación entre desarrolladores y mejoran mantenibilidad."),
            cq("¿Cuáles son los más preguntados en entrevistas?",
                "Singleton, Factory, Strategy, Observer, Repository, Builder, Dependency Injection (no es GoF pero se considera patrón). Spring implementa muchos patrones: Singleton (scope default), Factory (BeanFactory), Proxy (AOP), Template (JdbcTemplate)."),
            cq("¿Patrones creacionales vs estructurales vs comportamiento?",
                "Creacionales: cómo crear objetos (Singleton, Factory, Builder). Estructurales: cómo componer clases y objetos (Adapter, Decorator, Proxy). Comportamiento: cómo interactúan objetos (Strategy, Observer, Command)."));
        sc(designPatterns, "Singleton", "singleton", 1,
            "Garantiza una única instancia de una clase y proporciona un punto de acceso global. En Spring, los beans singleton son el scope por defecto.",
            """
            public class Configuracion {
                private static Configuracion instancia;

                private Configuracion() {}

                public static synchronized Configuracion getInstancia() {
                    if (instancia == null) {
                        instancia = new Configuracion();
                    }
                    return instancia;
                }
            }

            // Mejor con enum (thread-safe, anti-reflection)
            public enum ConfiguracionEnum {
                INSTANCE;
            }
            """,
            q("¿Problemas del Singleton?",
                "Dificulta testing (acoplamiento global), puede ocultar dependencias y causar problemas en entornos concurrentes si no es thread-safe. En Spring el singleton está bien gestionado por el contenedor."),
            q("¿Enum como Singleton?",
                "Joshua Bloch recomienda usar enum para singletons. Es serializable, thread-safe y evita ataques por reflection. Es la forma más robusta en Java."));
        sc(designPatterns, "Factory", "factory", 2,
            "Delega la creación de objetos a una clase factory en lugar de usar new directamente. Facilita añadir nuevas implementaciones sin modificar el código cliente.",
            """
            public interface Notificador {
                void enviar(String mensaje);
            }

            public class NotificadorFactory {
                public static Notificador crear(String tipo) {
                    return switch (tipo) {
                        case "email" -> new EmailNotificador();
                        case "sms" -> new SmsNotificador();
                        default -> throw new IllegalArgumentException();
                    };
                }
            }
            """,
            q("¿Factory vs Builder?",
                "Factory decide qué objeto crear. Builder construye un objeto complejo paso a paso. Factory method crea familias de objetos; Builder configura un solo objeto con muchos parámetros."),
            q("¿Dónde se usa Factory en Spring?",
                "BeanFactory y ApplicationContext actúan como factories de beans. También @Bean en @Configuration crea beans mediante factory method."));
        sc(designPatterns, "Strategy", "strategy", 3,
            "Define una familia de algoritmos, encapsula cada uno y los hace intercambiables. Permite cambiar comportamiento en tiempo de ejecución.",
            """
            public interface EstrategiaDescuento {
                BigDecimal aplicar(BigDecimal total);
            }

            public class DescuentoNavidad implements EstrategiaDescuento { ... }
            public class DescuentoEstudiante implements EstrategiaDescuento { ... }

            public class CalculadoraPrecio {
                public BigDecimal calcular(EstrategiaDescuento estrategia, BigDecimal total) {
                    return estrategia.aplicar(total);
                }
            }
            """,
            q("¿Strategy vs Polimorfismo?",
                "Strategy es una aplicación concreta de polimorfismo. Encapsula algoritmos intercambiables detrás de una interfaz común. Reduce if/else y switch."),
            q("¿Ejemplo en Spring?",
                "Inyección de dependencias por interfaz es Strategy. Elige la implementación con @Primary, @Qualifier o listas de beans según el contexto."));
        sc(designPatterns, "Observer", "observer", 4,
            "Define una dependencia uno-a-muchos entre objetos. Cuando el sujeto cambia, notifica automáticamente a todos los observadores. En Spring se implementa con ApplicationEvents.",
            """
            // Evento
            public record PedidoCreadoEvent(Long pedidoId) {}

            // Publicador
            @Service
            public class PedidoService {
                private final ApplicationEventPublisher publisher;

                public void crearPedido() {
                    // ...
                    publisher.publishEvent(new PedidoCreadoEvent(1L));
                }
            }

            // Observador
            @EventListener
            public void onPedidoCreado(PedidoCreadoEvent event) { ... }
            """,
            q("¿Observer vs Pub/Sub?",
                "Observer es un patrón de diseño orientado a objetos. Pub/Sub es una arquitectura más desacoplada con un broker intermediario (Kafka, RabbitMQ). Observer suele ser in-process."),
            q("¿@EventListener es asíncrono?",
                "Por defecto es síncrono. Para hacerlo asíncrono, añade @Async al listener y habilita @EnableAsync."));
        sc(designPatterns, "Repository", "repository-pattern", 5,
            "Patrón que abstrae el acceso a datos. Separa la lógica de negocio de la persistencia. Spring Data JPA implementa este patrón con interfaces como JpaRepository.",
            """
            public interface ClienteRepository {
                Optional<Cliente> findById(Long id);
                Cliente save(Cliente cliente);
                List<Cliente> findAll();
            }

            // Spring Data JPA lo implementa automáticamente
            public interface SpringClienteRepository extends JpaRepository<Cliente, Long> {}
            """,
            q("¿Repository vs DAO?",
                "DAO es más cercano a la BD y suele reflejar tablas. Repository es más orientado al dominio y puede agregar lógica de consulta. En la práctica con Spring Data se usan indistintamente."),
            q("¿Ventajas del patrón Repository?",
                "Facilita tests (puedes mockear el repositorio), centraliza acceso a datos y permite cambiar la tecnología de persistencia sin tocar la lógica de negocio."));

        // ===== REST API =====
        Concept restApi = concept("REST API", "rest-api", Block.SPRING, 5,
            "REST (Representational State Transfer) es un estilo arquitectónico para diseñar APIs web. Se basa en recursos identificados por URLs, métodos HTTP semánticos y representaciones (normalmente JSON).",
            null,
            cq("¿Qué es REST?",
                "Estilo arquitectónico donde los recursos se identifican con URLs y se manipulan con métodos HTTP. Es stateless: cada petición contiene toda la información necesaria."),
            cq("¿Métodos HTTP en REST?",
                "GET (leer), POST (crear), PUT/PATCH (actualizar), DELETE (eliminar). Deben usarse de forma semántica. GET y DELETE son idempotentes; PUT también lo es si reemplaza el recurso completo."),
            cq("¿Qué significa stateless?",
                "El servidor no guarda estado de sesión del cliente entre peticiones. Cada request es independiente. La autenticación via JWT es un ejemplo de stateless."));
        sc(restApi, "Métodos HTTP y status codes", "http-status", 1,
            "Usar los métodos y códigos correctamente es fundamental para APIs REST claras y predecibles.",
            """
            GET    /clientes        -> 200 OK
            GET    /clientes/1      -> 200 OK | 404 Not Found
            POST   /clientes        -> 201 Created | 400 Bad Request
            PUT    /clientes/1      -> 200 OK | 204 No Content | 404 Not Found
            PATCH  /clientes/1      -> 200 OK | 204 No Content
            DELETE /clientes/1      -> 204 No Content | 404 Not Found
            """,
            q("¿200 vs 201 vs 204?",
                "200: petición exitosa con body. 201: recurso creado exitosamente. 204: petición exitosa sin body (común en DELETE y PUT)."),
            q("¿400 vs 401 vs 403 vs 404?",
                "400: request mal formado. 401: no autenticado. 403: autenticado pero sin permisos. 404: recurso no encontrado. 409: conflicto (ej: recurso ya existe). 422: entidad no procesable (validación)."));
        sc(restApi, "Paginación y ordenación", "paginacion", 2,
            "Para colecciones grandes, expone paginación y ordenación en lugar de devolver todo. Spring Data Pageable facilita su implementación.",
            """
            GET /clientes?page=0&size=20&sort=nombre,asc

            // Respuesta típica
            {
              "content": [...],
              "pageable": { "pageNumber": 0, "pageSize": 20 },
              "totalElements": 150,
              "totalPages": 8
            }
            """,
            q("¿Page vs Slice?",
                "Page conoce el total de elementos y páginas (requiere count). Slice solo sabe si hay siguiente página, más eficiente cuando no necesitas el total."),
            q("¿Cómo implementar paginación en Spring?",
                "Añade Pageable como parámetro del controller o repository. Spring resuelve page, size y sort automáticamente desde query params."));
        sc(restApi, "Idempotencia y seguridad", "idempotencia", 3,
            "Idempotencia significa que repetir una operación produce el mismo resultado. GET, PUT y DELETE deben ser idempotentes. POST no lo es porque crea un nuevo recurso cada vez.",
            """
            // Idempotente: enviar dos veces da el mismo resultado
            PUT /clientes/1 { "nombre": "Ana" }

            // No idempotente: enviar dos veces crea dos recursos
            POST /clientes { "nombre": "Ana" }

            // Clave de idempotencia para POST
            POST /pagos
            Idempotency-Key: abc-123
            """,
            q("¿Por qué importa la idempotencia?",
                "Permite reintentar peticiones sin efectos secundarios. Crucial en pagos, reservas y operaciones críticas donde pueden ocurrir timeouts y reintentos."),
            q("¿GET es seguro e idempotente?",
                "Sí. Seguro porque no modifica estado. Idempotente porque repetirlo no cambia nada. POST no es seguro ni idempotente por defecto."));
        sc(restApi, "Versionado de APIs", "versionado-api", 4,
            "Estrategias para evitar romper clientes al evolucionar la API: path, query param, header o content negotiation.",
            """
            // Path versioning (más común)
            /api/v1/clientes
            /api/v2/clientes

            // Header versioning
            GET /clientes
            X-API-Version: 2

            // Query param
            /clientes?version=2
            """,
            q("¿Qué versión usar?",
                "Path versioning es el más simple y explícito. Header es más limpio para URLs pero menos visible. Query param es fácil de probar. No hay una única respuesta; depende de la organización."),
            q("¿Cuándo crear una nueva versión?",
                "Cuando hay cambios breaking: eliminar campos, cambiar tipos, modificar comportamiento. Añadir campos suele ser backward compatible y no requiere nueva versión."));

        // ===== PROGRAMACIÓN FUNCIONAL =====
        Concept programacionFuncional = concept("Programación Funcional", "programacion-funcional", Block.JAVA_CORE, 18,
            "Java 8 introdujo características funcionales: lambdas, method references e interfaces funcionales. Permiten escribir código más conciso y composable, especialmente con Streams y Optional.",
            null,
            cq("¿Qué es una lambda?",
                "Función anónima que puede pasarse como argumento. Sintaxis compacta para implementar interfaces funcionales. Ejemplo: (a, b) -> a + b."),
            cq("¿Qué son las interfaces funcionales?",
                "Interfaces con un único método abstracto. Marcadas con @FunctionalInterface. Ejemplos: Runnable, Callable, Predicate, Function, Consumer, Supplier. Java 8 añadió muchas en java.util.function."),
            cq("¿Method reference?",
                "Sintaxis abreviada para lambdas que llaman a un método existente. Ejemplos: System.out::println, String::toUpperCase, Cliente::getNombre."));
        sc(programacionFuncional, "Interfaces funcionales", "interfaces-funcionales", 1,
            "Categorías principales de interfaces funcionales en java.util.function.",
            """
            Predicate<String> empiezaConA = s -> s.startsWith("A");
            Function<String, Integer> longitud = String::length;
            Consumer<String> imprimir = System.out::println;
            Supplier<Double> aleatorio = Math::random;
            BiFunction<Integer, Integer, Integer> sumar = Integer::sum;

            // Uso
            imprimir.accept(empiezaConA.test("Ana") ? "Sí" : "No");
            """,
            q("¿Predicate vs Function?",
                "Predicate<T> recibe un T y devuelve boolean. Function<T, R> recibe T y devuelve R. Consumer<T> recibe T y no devuelve nada. Supplier<T> no recibe nada y devuelve T."),
            q("¿@FunctionalInterface obligatoria?",
                "No, pero es buena práctica. El compilador verifica que solo haya un método abstracto y genera error si hay más."));
        sc(programacionFuncional, "Lambdas", "lambdas", 2,
            "Expresiones lambda simplifican la implementación de interfaces funcionales. Son la base de Streams y operaciones funcionales.",
            """
            List<String> nombres = List.of("Ana", "Pedro", "María");

            // Lambda
            nombres.forEach(nombre -> System.out.println(nombre));

            // Method reference equivalente
            nombres.forEach(System.out::println);

            // Lambda con cuerpo
            nombres.stream()
                .filter(n -> {
                    String minuscula = n.toLowerCase();
                    return minuscula.length() > 3;
                })
                .toList();
            """,
            q("¿Variables efectivamente finales en lambdas?",
                "Las variables locales usadas en lambdas deben ser final o efectivamente finales. No puedes reasignarlas después de usarlas en la lambda."),
            q("¿this en lambda?",
                "this dentro de una lambda se refiere a la instancia de la clase contenedora, no a la lambda misma. Es diferente de las clases anónimas clásicas."));
        sc(programacionFuncional, "Method References", "method-references", 3,
            "Forma concisa de escribir lambdas que invocan un método existente. Hay cuatro tipos: estático, instancia, constructor y arbitraria.",
            """
            // Referencia a método estático
            Function<String, Integer> parsear = Integer::parseInt;

            // Referencia a método de instancia de objeto particular
            Cliente cliente = new Cliente("Ana");
            Supplier<String> nombre = cliente::getNombre;

            // Referencia a método de instancia arbitraria
            Function<String, String> mayusculas = String::toUpperCase;

            // Referencia a constructor
            Supplier<List<String>> lista = ArrayList::new;
            """,
            q("¿Cuándo usar method reference?",
                "Cuando la lambda solo llama a un método existente. Mejora legibilidad. Si la lambda tiene lógica adicional, mantén la sintaxis lambda."),
            q("¿Method reference siempre reemplaza lambda?",
                "No. Solo cuando la lambda es una llamada directa a un método. Para transformaciones más complejas, la lambda es más clara."));

        // ===== JVM / GARBAGE COLLECTION =====
        Concept jvmGc = concept("JVM y Garbage Collection", "jvm-gc", Block.JAVA_CORE, 19,
            "Comprender la JVM, la gestión de memoria y el garbage collection es clave para optimizar aplicaciones Java y diagnosticar problemas de rendimiento.",
            null,
            cq("¿Qué partes tiene la memoria de la JVM?",
                "Heap (objetos dinámicos), Stack (frames de métodos y variables locales), Metaspace (metadatos de clases, reemplazó a PermGen en Java 8), Program Counter, Native Method Stack."),
            cq("¿Qué es el Garbage Collector?",
                "Proceso automático de la JVM que libera memoria de objetos no alcanzables. Evita fugas de memoria y liberación manual. Diferentes algoritmos: Serial, Parallel, CMS, G1, ZGC, Shenandoah."),
            cq("¿Heap vs Stack?",
                "Heap almacena objetos y es compartido entre hilos. Stack almacena variables locales y referencias de métodos; cada hilo tiene su propio stack. Objetos grandes viven en heap."));
        sc(jvmGc, "Heap y generaciones", "heap-generaciones", 1,
            "El heap se divide en Young Generation (Eden, Survivor) y Old Generation. La mayoría de objetos mueren jóvenes; los que sobreviven varios ciclos GC pasan a Old Gen.",
            """
            // Regiones del heap
            Young Generation:
              - Eden: nuevos objetos
              - Survivor S0, S1: objetos que sobreviven a Minor GC
            Old Generation: objetos longevos

            // Minor GC: limpia Young Generation
            // Major/Full GC: limpia Old Generation
            """,
            q("¿Qué es un Minor GC?",
                "Recolección de basura en Young Generation. Es rápido porque la mayoría de objetos mueren pronto. Usa el algoritmo mark-copy."),
            q("¿Qué es un Full GC?",
                "Recolección en todo el heap, incluyendo Old Generation. Es más lento y puede causar pausas largas (stop-the-world). Se intenta minimizar en producción."));
        sc(jvmGc, "Garbage Collectors", "garbage-collectors", 2,
            "JVM ofrece varios GC con diferentes trade-offs entre throughput, latency y footprint.",
            """
            # Algunos collectors y flags
            -XX:+UseG1GC              // G1 Garbage Collector (default Java 11+)
            -XX:+UseZGC               // ZGC (baja latencia, Java 15+ production)
            -XX:+UseShenandoahGC      // Shenandoah (baja latencia)
            -XX:MaxGCPauseMillis=200  // objetivo de pausa para G1
            """,
            q("¿G1 vs ZGC?",
                "G1 divide el heap en regiones y balancea throughput y latencia. ZGC está diseñado para pausas menores a 10ms con heaps grandes, ideal para aplicaciones de baja latencia."),
            q("¿Qué GC usar en producción?",
                "Java 17+: G1 es el default y funciona bien para la mayoría. Para aplicaciones con requisitos estrictos de latencia y heaps muy grandes, considera ZGC o Shenandoah."));
        sc(jvmGc, "Memory leaks en Java", "memory-leaks", 3,
            "Aunque Java tiene GC, pueden producirse fugas de memoria si se mantienen referencias no intencionadas a objetos.",
            """
            // Ejemplo clásico: colección estática que crece
            public class Cache {
                private static final Map<String, Object> cache = new HashMap<>();
                // Si nunca se eliminan entradas, esto es un memory leak
            }

            // Solución: WeakHashMap o caché con TTL/eviction
            private static final Map<String, Object> cache = new WeakHashMap<>();
            """,
            q("¿Cómo detectar memory leak?",
                "Herramientas: heap dumps + Eclipse MAT, VisualVM, JProfiler. Síntomas: uso de heap que crece sin liberarse, OutOfMemoryError, Full GC frecuentes."),
            q("¿WeakReference vs SoftReference?",
                "WeakReference permite que el GC recolecte el objeto en el próximo ciclo. SoftReference se mantiene hasta que la JVM necesita memoria. Útil para caches."));
        sc(jvmGc, "JVM tuning básico", "jvm-tuning", 4,
            "Opciones básicas de configuración de memoria y comportamiento del GC.",
            """
            # Opciones comunes
            -Xms512m -Xmx2g       // heap inicial y máximo
            -XX:MetaspaceSize=128m
            -XX:MaxMetaspaceSize=256m
            -XX:+HeapDumpOnOutOfMemoryError
            -XX:HeapDumpPath=/tmp/heapdump.hprof
            -Xlog:gc*:file=/tmp/gc.log
            """,
            q("¿Xms debe ser igual a Xmx?",
                "En producción suele recomendarse Xms = Xmx para evitar resizing del heap, que genera pausas. Pero depende de la carga y elasticidad del entorno."),
            q("¿Qué es un heap dump?",
                "Snapshot de la memoria heap en un momento dado. Permite analizar qué objetos consumen memoria y quién los referencia. Esencial para diagnosticar OOM."));

        // ===== SERIALIZACIÓN =====
        Concept serializacion = concept("Serialización", "serializacion", Block.JAVA_CORE, 20,
            "Serialización convierte un objeto en una secuencia de bytes para almacenarlo o transmitirlo. Deserialización reconstruye el objeto a partir de esos bytes.",
            null,
            cq("¿Qué es Serializable?",
                "Interfaz marker de java.io. Indica que una clase puede serializarse. Requiere que todos los campos sean serializables o transient."),
            cq("¿Qué es transient?",
                "Palabra clave que evita que un campo se serialice. Útil para datos sensibles, cachés o campos que no tienen sentido fuera de la JVM actual."),
            cq("¿serialVersionUID?",
                "Identificador de versión para clases serializables. Si cambia la clase, debe coincidir al deserializar o lanzará InvalidClassException. Se recomienda declararlo explícitamente."));
        sc(serializacion, "Serializable básico", "serializable-basico", 1,
            "Ejemplo mínimo de serialización con ObjectOutputStream y ObjectInputStream.",
            """
            public class Usuario implements Serializable {
                private static final long serialVersionUID = 1L;
                private String nombre;
                private transient String password; // no se serializa
            }

            // Guardar
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("user.ser"))) {
                oos.writeObject(usuario);
            }

            // Recuperar
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("user.ser"))) {
                Usuario u = (Usuario) ois.readObject();
            }
            """,
            q("¿Por qué usar try-with-resources?",
                "Garantiza que los streams se cierren automáticamente. ObjectOutputStream y ObjectInputStream deben cerrarse para evitar fugas de recursos."),
            q("¿Serializable sigue siendo recomendado?",
                "Para persistencia o comunicación entre servicios modernos se prefieren JSON (Jackson) o Protocol Buffers. Serializable sigue útil para cachés locales, sesiones o RMI."));

        // ===== PASO POR VALOR Y REFERENCIA =====
        Concept pasoValorRef = concept("Paso por Valor y Referencia", "paso-valor-referencia", Block.JAVA_CORE, 21,
            "Java siempre pasa por VALOR, nunca por referencia. Para tipos primitivos, se copia el valor. Para objetos, se copia la referencia (el puntero), no el objeto en sí. Esto causa confusión porque parece referencia, pero es una copia de la referencia.",
            null,
            cq("¿Java es pass-by-value o pass-by-reference?",
                "Java es 100% pass-by-value. Para primitivos se copia el valor. Para objetos se copia la referencia (dirección de memoria). Nunca se pasa el objeto directamente. Por eso cambiar la referencia interna de un método no afecta al llamador."),
            cq("¿Por qué parece que Java pasa objetos por referencia?",
                "Porque modificas el objeto al que apunta la referencia. Pero si dentro del método haces obj = new Object(), solo cambias la copia local de la referencia. El llamador sigue apuntando al objeto original. La confusión viene de modificar estado interno del objeto."),
            cq("¿Ejemplo clásico de confusión?",
                "swap(int a, int b) { int temp = a; a = b; b = temp; } - No funciona. Pero si haces obj.campo = valor, SÍ funciona porque modificas el objeto, no la referencia.");
        sc(pasoValorRef, "Primitivos vs Referencias", "primitivos-vs-referencias", 1,
            "Diferencia fundamental entre cómo se tratan tipos primitivos y objetos.",
            """
            // PRIMITIVOS: se copia el valor
            void duplicar(int n) {
                n = n * 2;  // solo cambia LOCAL
            }
            int x = 5;
            duplicar(x);
            System.out.println(x);  // sigue siendo 5

            // OBJETOS: se copia la referencia
            void modificar(StringBuilder sb) {
                sb.append(" mundo");  // SÍ modifica el objeto original
            }
            StringBuilder sb = new StringBuilder("hola");
            modificar(sb);
            System.out.println(sb);  // "hola mundo"

            // PERO esto NO funciona:
            void cambiar(StringBuilder sb) {
                sb = new StringBuilder("nuevo");  // solo cambia la copia local
            }
            StringBuilder original = new StringBuilder("hola");
            cambiar(original);
            System.out.println(original);  // sigue siendo "hola"
            """,
            q("¿Por qué el ejemplo del StringBuilder modifica el objeto?",
                "Porque sb apunta al mismo objeto en memoria que original. Cuando llamas sb.append(), operas sobre el objeto al que apunta sb. Como sb y original apuntan al mismo objeto, los cambios son visibles desde original."),
            q("¿Qué significa 'copiar la referencia'?",
                "La referencia es la dirección de memoria (pointer). Cuando pasas un objeto, se copia esa dirección. Ahora tienes dos referencias apuntando al mismo objeto. Si una referencia cambia de objeto, la otra no se entera."),
            q("¿Cómo simular swap real en Java?",
                "No puedes directamente. Solo puedes: 1) Devolver los valores intercambiados, 2) Usar un wrapper, 3) Usar arrays de un elemento. Java no soporta true pass-by-reference como C++.")
        );
        sc(pasoValorRef, "Inmutabilidad y efectos", "inmutabilidad-efectos", 2,
            "Con objetos mutables puedes 'ver' efectos laterales. Con inmutables (String, Integer), nunca hay confusión porque no puedes modificarlos.",
            """
            // String es inmutable: no hay efecto lateral
            void procesar(String s) {
                s = s + " mundo";  // crea NUEVO String, no modifica original
            }
            String texto = "hola";
            procesar(texto);
            System.out.println(texto);  // "hola" - sin cambios

            // Wrapper array para simular pass-by-reference
            void swap(String[] arr) {
                String temp = arr[0];
                arr[0] = arr[1];
                arr[1] = temp;
            }
            String[] par = {"a", "b"};
            swap(par);
            System.out.println(par[0] + par[1]);  // "ba" - SÍ funciona
            """,
            q("¿Por qué String no se comporta como el StringBuilder?",
                "String es inmutable: todos sus métodos que 'modifican' en realidad crean nuevos String. No hay forma de cambiar el contenido de un String existente. StringBuilder es mutable, sus métodos modifican el array interno."),
            q("¿Integer es mutable o inmutable?",
                "Inmutable. Cuando haces operaciones aritméticas, se crean nuevos Integer. No puedes hacer intWrapper.value++ y esperar que el original se modifique."),
            q("¿Ventaja de la inmutabilidad?",
                "Thread-safe por diseño, sin sincronización. Sin efectos laterales inesperados. Objetos inmutables son predecibles y seguros para caching y uso como claves de HashMap.")
        );

        // ===== INTERFACES FUNCIONALES =====
        Concept functionalInterfaces = concept("Interfaces Funcionales", "interfaces-funcionales", Block.JAVA_CORE, 22,
            "Una interfaz funcional tiene exactamente un método abstracto. Es la base para lambda expressions y method references (Java 8). @FunctionalInterface marca interfaces intencionalmente funcionales y fuerza al compilador a verificar que solo tienen un método abstracto. El paquete java.util.function contiene las más comunes.",
            null,
            cq("¿Qué es una interfaz funcional?",
                "Interfaz con un único método abstracto. Puede tener métodos default, static o private (desde Java 9). @FunctionalInterface es optional pero vérifies en compile-time que cumple el contrato. Permiten usar lambda expressions: () -> expresion o (a, b) -> bloque."),
            cq("¿Por qué existen las interfaces funcionales?",
                "Son el target de las lambda expressions. Antes de lambdas, se usaban anonymous inner classes para comportamiento. Lambdas proporcionan sintaxis más concisa. java.util.function ofrece interfaces genéricas predefinidas: Function, Consumer, Supplier, Predicate, y sus variantes primitivas."),
            cq("¿Cuál es la diferencia entre Function, Consumer, Supplier y Predicate?",
                "Function<T,R>: acepta T, devuelve R. Consumer<T>: acepta T, no devuelve nada (side effects). Supplier<T>: no acepta nada, devuelve T. Predicate<T>: acepta T, devuelve boolean (test). Todos en java.util.function.")
        );
        sc(functionalInterfaces, "java.util.function principales", "functional-interface-common", 1,
            "Las cuatro interfaces funcionales más usadas del JDK.",
            """
            import java.util.function.*;

            // Function<T,R>: T -> R
            Function<String, Integer> length = String::length;
            length.apply("hola");  // 4

            // Consumer<T>: T -> void
            Consumer<String> printer = System.out::println;
            printer.accept("hola");  // imprime

            // Supplier<T>: () -> T
            Supplier<LocalDate> now = LocalDate::now;
            now.get();  // fecha actual

            // Predicate<T>: T -> boolean
            Predicate<String> isEmpty = String::isEmpty;
            isEmpty.test("");  // true

            // BiFunction<T,U,R>: (T,U) -> R
            BiFunction<Integer, Integer, Integer> sum = Integer::sum;

            // UnaryOperator<T>: T -> T (extiende Function<T,T>)
            UnaryOperator<Integer> doubleIt = n -> n * 2;

            // BinaryOperator<T>: (T,T) -> T (extiende BiFunction<T,T,T>)
            BinaryOperator<Integer> max = Integer::max;
            """,
            q("¿Qué es un BiConsumer, BiFunction, BiPredicate?",
                "Variantes que aceptan dos argumentos. BiConsumer<T,U>: (T,U) -> void. BiFunction<T,U,R>: (T,U) -> R. BiPredicate<T,U>: (T,U) -> boolean. Útiles cuando necesitas operar con dos valores."),
            q("¿Qué son las variantes primitivas (IntFunction, IntConsumer, etc.)?",
                "Evitan boxing/unboxing para tipos primitivos. IntFunction<R> acepta int y devuelve R. IntConsumer acepta int. IntPredicate acepta int. Hay versiones para int, long, double (Int-, Long-, Double-). Mejora rendimiento al evitar autoboxing en streams o cálculos.")
        );
        sc(functionalInterfaces, "Lambdas y Method References", "lambdas-method-references", 2,
            "Lambda expression: función anónima que implementa interfaz funcional. Method reference: atajo para escribir lambdas simples.",
            """
            // Lambda: expresión que implementa el único método
            Predicate<String> p1 = s -> s.isEmpty();           // lambda
            Predicate<String> p2 = String::isEmpty;             // method reference

            // Tipos de method reference
            List<String> nombres = List.of("Ana", "Pedro");

            // 1. Static: ContainingClass::staticMethod
            Function<String, Integer> parse = Integer::parseInt;

            // 2. Instance method of particular object:
            String prefix = "Hola";
            Function<String, String> concat = prefix::concat;

            // 3. Instance method of arbitrary object of particular type:
            Stream<String> s = nombres.stream();
            s.map(String::toUpperCase);  // cada String::toUpperCase

            // 4. Constructor reference:
            Supplier<ArrayList> listFactory = ArrayList::new;

            // Lambdas con cuerpo bloque
            Comparator<Integer> cmp = (a, b) -> {
                if (a < b) return -1;
                if (a > b) return 1;
                return 0;
            };
            """,
            q("¿Cuándo usar method reference vs lambda?",
                "Method reference cuando llamas a un método existente que ya hace lo que necesitas (String::isBlank, System.out::println). Lambda cuando necesitas lógica personalizada que no es un método existente. Method reference es más conciso y preferido cuando es posible."),
            q("¿Qué es effectively final?",
                "Variables locales usadas dentro de una lambda deben ser effectively final (no modificadas después de su última asignación). El compilador lo exige. Esto es porque la lambda puede outlive el scope del método si se almacena. Garantiza thread-safety."),
            q("¿Lambda vs Anonymous inner class?",
                "Lambda tiene sintaxis más limpia. Anonymous class puede tener estado de instancia propio, múltiples métodos (si no es funcional), y puede acceder a variables del enclosing scope de forma diferente. Lambda no tiene estado propio; anonymous class sí.")
        );
        sc(functionalInterfaces, "Chaining y Compose", "functional-interface-chaining", 3,
            "Methods default como andThen, compose, and, or para combinar funciones y predicates.",
            """
            // Function: andThen (ejecuta THIS primero, luego other)
            Function<Integer, Integer> multiply = x -> x * 2;
            Function<Integer, Integer> add = x -> x + 10;
            Function<Integer, Integer> combined = multiply.andThen(add);
            combined.apply(5);  // (5 * 2) + 10 = 20

            // Function: compose (ejecuta other primero, luego THIS)
            Function<Integer, Integer> composed = multiply.compose(add);
            composed.apply(5);  // (5 + 10) * 2 = 30

            // Predicate: and, or, negate
            Predicate<String> startsWithA = s -> s.startsWith("A");
            Predicate<String> endsWithZ = s -> s.endsWith("Z");
            Predicate<String> aToZ = startsWithA.and(endsWithZ);
            Predicate<String> notAZ = aToZ.negate();

            // Consumer: andThen
            Consumer<String> first = s -> System.out.println("1: " + s);
            Consumer<String> second = s -> System.out.println("2: " + s);
            Consumer<String> both = first.andThen(second);
            both.accept("Hola");  // imprime ambas líneas
            """,
            q("¿andThen vs compose en Function?",
                "f.andThen(g) ejecuta f y luego g sobre el resultado de f. f.compose(g) ejecuta g primero y luego f sobre el resultado de g. Ej: multiply.andThen(add) = add(multiply(x)). multiply.compose(add) = multiply(add(x))."),
            q("¿Por qué usar chaining?",
                "Permite construir pipelines de procesamiento sin clases intermedias. Más legible que nested anonymous classes. Facilita composición de comportamiento reusable. Patterns como decorator se implementan naturalmente.")
        );

        // ===== STRING STRINGBUILDER STRINGBUFFER =====
        Concept stringStringBuilder = concept("String vs StringBuilder vs StringBuffer", "string-stringbuilder-stringbuffer", Block.JAVA_CORE, 23,
            "String es inmutable en Java. Cada concatenación crea un nuevo objeto String, lo que genera overhead de memoria y垃圾 collection para muchos cambios. StringBuilder es mutable y no sincronizado (no thread-safe), ideal para single-thread. StringBuffer es mutable y sincronizado (thread-safe), pero más lento por la sincronización.",
            null,
            cq("¿Por qué String es inmutable en Java?",
                "1) Security: URLs, connections, file paths no pueden cambiarse por otro thread. 2) Thread-safety: sin sincronización necesaria. 3) String pool: permite caching de Strings literales sin riesgo de que un thread modifique el de otro. 4) HashMap/HashSet: pueden usar String como clave porque es inmutable. 5) Class loading: el classpath es String, no puede alterarse."),
            cq("¿Qué es el String Pool?",
                "Heap space especial donde Java almacena String literals. Cuando creas String literal, JVM busca en el pool; si existe, reutiliza la referencia. Esto ahorra memoria. String s = \"hola\" usa pool. new String(\"hola\") crea objeto en heap normal, no en pool. intern() mueve un String del heap al pool."),
            cq("¿Cuándo usar cada uno?",
                "String: cuando el valor no cambia o cambia poco. StringBuilder: cuando construyes strings en single-thread (la mayoría de casos), loops, builder pattern. StringBuffer: cuando necesitas thread-safety y múltiples threads modifican el mismo string (raro, rara vez necesario hoy en día).")
        );
        sc(stringStringBuilder, "String: inmutable", "string-inmutable", 1,
            "String es inmutable. Cada operación que parece modificar ('+', concat, replace) crea un NUEVO String. El original permanece igual.",
            """
            String s = "hola";
            s.concat(" mundo");      // crea nuevo String, no modifica s
            s = s + " mundo";        // s ahora apunta al nuevo String

            // String pool
            String a = "hola";
            String b = "hola";
            System.out.println(a == b);  // true (misma referencia del pool)

            String c = new String("hola");
            System.out.println(a == c);  // false (c es objeto nuevo en heap)

            c = c.intern();              // c ahora apunta al pool
            System.out.println(a == c);  // true

            // Strings son ideales como claves de HashMap
            Map<String, Integer> mapa = new HashMap<>();
            mapa.put("clave", 1);  // funciona porque String es inmutable
            """,
            q("¿Métodos que PARECEN modificar String pero no lo hacen?",
                "replace(), substring(), concat(), toLowerCase(), toUpperCase(), trim(), strip() todos devuelven NUEVO String. El original queda intacto. Por eso String es seguro para multi-thread sin sincronización."),
            q("¿Qué es intern() de String?",
                "String.intern() busca el String en el String pool. Si existe, devuelve la referencia del pool. Si no, añade el String al pool y devuelve la referencia. Útil para guardar memoria cuando tienes muchos Strings duplicados en heap.")
        );
        sc(stringStringBuilder, "StringBuilder: mutable y rápido", "stringbuilder", 2,
            "StringBuilder es mutable. Sus métodos (append, insert, delete, reverse) MODIFICAN el buffer interno SIN crear nuevos objetos. Más eficiente para concatenaciones frecuentes.",
            """
            StringBuilder sb = new StringBuilder("hola");
            sb.append(" mundo");       // modifica el buffer interno
            sb.insert(0, "DIOS: ");   // inserta en posición
            sb.delete(0, 5);           // elimina caracteres [0,5)
            sb.reverse();             // invierte el string

            String resultado = sb.toString();  // convierte a String

            // Capacidad: StringBuilder grow automáticamente
            StringBuilder sb2 = new StringBuilder();  // capacidad default 16
            StringBuilder sb3 = new StringBuilder("hola");  // capacidad 16 + 4 = 20
            StringBuilder sb4 = new StringBuilder(50);  // capacidad inicial 50

            // Benchmark mental:
            // String s = ""; for(int i=0;i<10000;i++) s += i;  // MAL: 10000 objetos
            // StringBuilder sb = new StringBuilder(); for(int i=0;i<10000;i++) sb.append(i); // BIEN
            """,
            q("¿Cuánta más memoria usa StringBuilder que String para muchas operaciones?",
                "Stringpool guarda literales sin overhead extra. Pero si construyes muchos strings temporales, String pool no ayuda; StringBuilder es mejor porque no crea N objetos intermediarios (crea 1 buffer que crece). Un String de 10KB concatenado 1000 veces: String = ~1000 objetos, StringBuilder = 1 con growth occasional."),
            q("¿StringBuilder es thread-safe?",
                "NO. Sus métodos no están sincronizados. Si múltiples threads acceden y modifican el mismo StringBuilder sin coordinación, puedes ver datos parcialmente escritos (torn read). Solo úsalo en single-thread o sincroniza externamente.")
        );
        sc(stringStringBuilder, "StringBuffer: synchronized", "stringbuffer", 3,
            "StringBuffer es como StringBuilder pero CON synchronized en todos sus métodos. Thread-safe pero más lento.留下来的原因是 backward compatibility; hoy en día raramente necesario.",
            """
            StringBuffer sb = new StringBuffer("hola");

            // Todos los métodos están synchronized:
            synchronized(sb) {
                sb.append(" mundo");       // solo un thread entra aquí a la vez
                sb.insert(0, "DIOS: ");
                sb.delete(0, 5);
            }

            // Mismos métodos que StringBuilder pero thread-safe
            // Constructor y toString() NO están synchronized (no necesitan)

            // Hoy en día:
            // - Si necesitas thread-safety, considera StringJoiner o Stream.collect()
            // - O usa streams para construir strings:.collect(Collectors.joining())
            // - O StringJoiner (Java 8+) para unir con delimitadores
            """,
            q("¿StringBuffer vs synchronized StringBuilder manual?",
                "StringBuffer tiene synchronized en CADA método. Hacer synchronized yourself en StringBuilder tiene el mismo efecto pero más verbose. StringBuffer existe por legacy; no hay ventaja real sobre StringBuilder + synchronized externo hoy."),
            q("¿Alternativas modernas a StringBuffer?",
                "StringJoiner (Java 8+): para unir strings con delimitador. Collectors.joining() en streams: stream.map(...).collect(Collectors.joining(\", \")). Más idiomático para Java moderno. Para logs o builders complejos, StringBuilder sigue siendo la opción.")
        );
        sc(stringStringBuilder, "StringJoiner yjoining()", "stringjoiner", 4,
            "Java 8+: StringJoiner para unir strings con delimitador, prefijo y Sufijo. Collectors.joining() es la versión stream.",
            """
            // StringJoiner
            StringJoiner joiner = new StringJoiner(\", \", \"[\", \"]\");
            joiner.add(\"a\").add(\"b\").add(\"c\");
            System.out.println(joiner.toString());  // [a, b, c]

            // Collectors.joining() - más común en streams
            List<String> nombres = List.of(\"Ana\", \"Pedro\", \"María\");
            String csv = nombres.stream()
                .collect(Collectors.joining(\", \"));
            // \"Ana, Pedro, María\"

            String withPrefix = nombres.stream()
                .collect(Collectors.joining(\", \", \"[\", \"]\"));
            // \"[Ana, Pedro, María]\"

            // Antes de Java 8:
            StringBuilder sb = new StringBuilder();
            for (String n : nombres) {
                if (sb.length() > 0) sb.append(\", \");
                sb.append(n);
            }
            """,
            q("¿Collectors.joining() vs StringBuilder en loops?",
                "joining() es más legible para casos simples. Internamente usa StringJoiner que es similar a StringBuilder. Para loops simples, joining() gana en legibilidad. Para lógica compleja con condiciones, StringBuilder sigue siendo apropiado.")
        );

        // ===== OPTIONAL =====
        Concept optionalConcept = concept("Optional\<T\>", "optional", Block.JAVA_CORE, 24,
            "Optional (Java 8+) es un contenedor que puede contener o no un valor no nulo. Diseñado para evitar NullPointerException y eliminar null checks dispersos por el código. Obliga a tratar el caso de ausencia explícitamente. Es inmutable y thread-safe.",
            null,
            cq("¿Qué problema resuelve Optional?",
                "Optional elimina null checks dispersos (if (obj != null)). En lugar de null, usas Optional.empty(). Obliga al desarrollador a pensar en el caso de ausencia. Reduce NullPointerException. No es un reemplazo universal de null (no uses Optional para campos de entity, solo para retornos de métodos)."),
            cq("¿Cuándo NO usar Optional?",
                "NO usar Optional en: 1) Campos de entidades (@Entity), 2) Parámetros de método (preferible overloads o default values), 3) Colecciones (una colección vacía es mejor que Optional<List>), 4) Como clave de Map (Optional<KEY> es raro). Úsalo en RETORNOS de métodos donde la ausencia es semánticamente significativa."),
            cq("¿Optional es un reemplazo de null?",
                "Optional NO es un reemplazo universal de null. null sigue siendo válido para indicar 'no hay valor' en muchos contextos legacy. Optional es una mejora semántica: dice 'aquí puede no haber valor, trátalo'. Para campos de clase, sigue siendo mejor null + @NonNull que Optional<T> (menos overhead).")
        );
        sc(optionalConcept, "Creación y métodos básicos", "optional-basico", 1,
            "Formas de crear Optional y métodos para consultar su estado.",
            """
            import java.util.Optional;

            // Creación
            Optional<String> vacio = Optional.empty();
            Optional<String> nombre = Optional.of(\"Juan\");  // NO acepta null
            Optional<String> nullable = Optional.ofNullable(\"Pedro\");  // Acepta null

            //ofNullable es el más seguro: si sabes que puede ser null, usalo
            Optional<String> seguro = Optional.ofNullable(maybeNull);

            // Consultar
            nombre.isPresent();      // true
            nombre.isEmpty();        // false (Java 11+)
            nombre.get();            // \"Juan\" (lanza NoSuchElementException si empty)

            // IF presente
            nombre.ifPresent(n -> System.out.println(n.length()));  // imprime 4
            nombre.ifPresentOrElse(
                n -> System.out.println(n),
                () -> System.out.println(\"no hay nombre\")  // acción si empty
            );
            """,
            q("¿of vs ofNullable?",
                "Optional.of(value) lanza NullPointerException si value es null. Optional.ofNullable(value) devuelve Optional.empty() si value es null. Usa of() cuando SEPAS que el valor no es null. Usa ofNullable() cuando no estés seguro o venga de una fuente potencialmente null."),
            q("¿get() vs orElse()?",
                "get() lanza NoSuchElementException si empty. orElse(default) devuelve default si empty, o el valor si presente. orElseGet(supplier) es lazily evaluated (solo llama supplier si empty). orElseThrow() lanza excepción personalizada. orElse() siempre evalúa el argumento; orElseGet() solo si necesario.")
        );
        sc(optionalConcept, "Transformación y encadenamiento", "optional-encadenamiento", 2,
            "map, flatMap, filter para transformar y encadenar operaciones sin null checks.",
            """
            Optional<Person> person = Optional.ofNullable(getPerson());

            // map: transforma el valor si presente
            Optional<String> ciudad = person.map(Person::getCiudad);
            // ciudad es Optional<String>, empty si person era empty

            // flatMap: cuando la transformación devuelve Optional
            Optional<String> telefono = person.flatMap(Person::getTelefono);
            // getTelefono() ya devuelve Optional<String>

            // filter: mantiene valor si pasa el test
            Optional<Person> adulto = person.filter(p -> p.getEdad() >= 18);

            // Encadenamiento completo
            String result = Optional.ofNullable(getPerson())
                .map(Person::getCiudad)           // Optional<String>
                .flatMap(this::getCiudadOptional) // Optional<String>
                .map(String::toUpperCase)         // Optional<String>
                .orElse(\"DESCONOCIDA\");

            // Equivalente sin Optional:
            Person p = getPerson();
            String result2 = \"DESCONOCIDA\";
            if (p != null) {
                Ciudad c = p.getCiudad();
                if (c != null) {
                    String nombre = c.getNombre();
                    if (nombre != null) result2 = nombre.toUpperCase();
                }
            }
            """,
            q("¿map vs flatMap en Optional?",
                "map transforma el valor directamente. flatMap transforma Y 'aplana' un nivel: el supplier devuelve Optional, y flatMap evita envolverlo en otro Optional. Si tu transformación devuelve Optional<X>, usa flatMap. Si devuelve X, usa map."),
            q("¿Por qué Optional reduce null checks?",
                "Cada map/flatMap/filter encadenado solo se ejecuta si hay valor. Es como una cadena de null checks pero expresada de forma funcional. El código es más corto, más legible, y el compilador te fuerza a tratar el empty case con orElse/orElseGet/orElseThrow.")
        );
        sc(optionalConcept, "orElse, orElseGet, orElseThrow", "optional-orelse", 3,
            "Manejo de valores por defecto y excepciones en Optional.",
            """
            Optional<String> opt = Optional.ofNullable(getNullableString());

            // orElse: SIEMPRE evaluated (aunque el Optional tenga valor)
            String a = opt.orElse(getDefaultString());  // getDefaultString() siempre llamado

            // orElseGet: lazily evaluated (solo si Optional empty)
            String b = opt.orElseGet(() -> getDefaultString());  // solo si empty

            // orElseThrow: lanza excepción si empty
            String c = opt.orElseThrow(() -> new RuntimeException(\"no hay valor\"));

            // orElseThrow sin lambda (Java 10+)
            String d = opt.orElseThrow();  // lanza NoSuchElementException

            // ¿Cuándo usar cada uno?
            // - orElse: valor default simple (literales, constantes)
            // - orElseGet: cálculo costoso del default (solo si necesario)
            // - orElseThrow: la ausencia es una situación excepcional
            """,
            q("¿Cuál es la diferencia de rendimiento entre orElse y orElseGet?",
                "orElse siempre evalúa su argumento, incluso si el Optional tiene valor. Si el argumento es costoso (new String(\"...\"), llamada a método cara), usas orElseGet para evitar el coste cuando no es necesario. En valores simples (literales), no hay diferencia medible."),
            q("¿Optional tiene método isPresent()?",
                "Sí, pero evita usarlo. if (opt.isPresent()) { return opt.get(); } es más largo que opt.orElse(). Solo usa isPresent() si necesitas hacer DOS cosas diferentes: una si presente y otra si ausente, no solo devolver el valor. Para eso es mejor ifPresentOrElse().")
        );

        // ===== SEALED CLASSES =====
        Concept sealedClasses = concept("Sealed Classes", "sealed-classes", Block.JAVA_CORE, 25,
            "Sealed classes (Java 17) restringen qué clases pueden heredar de una clase o implementar una interfaz. Sedeclaran con 'sealed' y 'permits' para listar las subclases permitidas. El compilador conoce todas las subclases en compile-time, permitiendo exhaustive switch expressions y mejor exhaustiveness checking.",
            null,
            cq("¿Qué problema resuelven las Sealed Classes?",
                "Antes de sealed, cualquier clase podía extender cualquier clase (salvo final). Esto hacía difícil garantizar en compile-time que todas las subclases estaban cubiertas en un switch o cuando se usaban pattern matching. Sealed fuerza una jerarquía cerrada: solo las clases permitidas pueden extender/teor. El compilador puede verificar exhaustivamente."),
            cq("¿Cuál es la diferencia entre sealed, final y abstract?",
                "final: nadie puede heredar. sealed: solo las clases 'permits' pueden heredar, y esas deben ser final, sealed o non-sealed. abstract: puede heredarse libremente. sealed combina restricción Y flexibilidad controlada:密封 pero con subclases explícitas."),
            cq("¿Qué significa 'exhaustive switch'?",
                "Un switch sobre tipos sealed DEBE cubrir todos los casos permitidos (o tener default). Si olvidas un caso, el compilador error. Esto es typesafe: no puedes olvidarte de un caso. Muy útil con pattern matching en switch (Java 21+).")
        );
        sc(sealedClasses, "Sintaxis básica", "sealed-basico", 1,
            "Sealed class declara qué subclases pueden extender. Cada subclase debe ser final, sealed o non-sealed.",
            """
            // Clase sellada: solo Circle, Square, Rectangle pueden extenderla
            public sealed class Shape permits Circle, Square, Rectangle {
                public abstract double area();
            }

            // Subclase final: no puede ser extendida
            public final class Circle extends Shape {
                private double radius;
                public Circle(double radius) { this.radius = radius; }
                public double area() { return Math.PI * radius * radius; }
            }

            // Subclase sealed: puede ser extendida solo por las que ella permita
            public sealed class Rectangle extends Shape permits ColoredRectangle {
                private double width, height;
                public Rectangle(double w, double h) { width = w; height = h; }
                public double area() { return width * height; }
            }

            // Subclase non-sealed: queda abierta, cualquiera puede extender
            public non-sealed class Square extends Rectangle {
                private double side;
                public Square(double side) { super(side, side); this.side = side; }
            }

            // ColoredRectangle puede extender Square (que es non-sealed)
            public class ColoredRectangle extends Square {
                private String color;
                // ...
            }
            """,
            q("¿Por qué cada subclase debe ser final, sealed o non-sealed?",
                "Porque sealed define una jerarquía CERRADA. Cada subclase decide cómo continuar: final (cierra la rama), sealed (continue sellando hacia abajo), non-sealed (abre la jerarquía de nuevo). Si no especificaras esto, el compilador no sabría si puedes seguir heredando."),
            q("¿Sealed funciona con interfaces?",
                "Sí. Sealed interface restringe qué implementaciones puede tener. sealed interface Expr permits AddExpr, MulExpr, NumExpr {}. Las implementaciones deben ser final, sealed o non-sealed.")
        );
        sc(sealedClasses, "Pattern Matching Exhaustivo", "sealed-pattern-matching", 2,
            "Java 17 permite switch expressions que son exhaustivas sobre tipos sealed. El compilador verifica que cubres todos los casos.",
            """
            // Java 21+ switch con pattern matching (exhaustive)
            public String describe(Shape s) {
                return switch (s) {
                    case Circle c    -> \"Círculo radio=\" + c.radius();
                    case Square sq   -> \"Cuadrado lado=\" + sq.side();
                    case Rectangle r -> \"Rectángulo \" + r.width() + \"x\" + r.height();
                    // No necesita default: el compilador sabe que están TODOS los casos
                };
            }

            // Java 17 (sin pattern matching):
            public String describe(Shape s) {
                if (s instanceof Circle) {
                    Circle c = (Circle) s;
                    return \"Círculo\";
                } else if (s instanceof Square) {
                    return \"Cuadrado\";
                } // ...
            }

            // null en sealed switch
            public String describeWithNull(Shape s) {
                return switch (s) {
                    case null         -> \"Es null\";
                    case Circle c     -> \"Círculo\";
                    case Square sq    -> \"Cuadrado\";
                    // null debe manejarse explícitamente desde Java 21
                };
            }
            """,
            q("¿Qué es exhaustive checking?",
                "El compilador verifica que un switch sobre tipos sealed cubra TODOS los casos permitidos. Si olvidas uno, error de compilación. Esto es mejor que default (que silenciosamente podría no cubrir un caso nuevo). Es typesafe: si añades una nueva subclase, todos los switches que no la cubran fallan en compilación."),
            q("¿Sealed class puede ser abstract?",
                "Sí. Puede tener métodos abstractos. Las subclases concretas deben implementarlos. Una sealed abstract Shape con método abstract area() obliga a cada subclase a proporcionar el cálculo.")
        );
        sc(sealedClasses, "Uso con Records y Pattern Matching", "sealed-records", 3,
            "Records y sealed classes se complementan bien. Records son final e inmutables por defecto, ideales como subclases de sealed.",
            """
            // Sealed + Records: combinación poderosa
            public sealed interface Operation permits Add, Multiply, Constant {
                double apply(double x, double y);
            }

            public record Add() implements Operation {
                public double apply(double x, double y) { return x + y; }
            }

            public record Multiply() implements Operation {
                public double apply(double x, double y) { return x * y; }
            }

            public record Constant(double value) implements Operation {
                public double apply(double x, double y) { return value; }
            }

            // El compilador sabe que solo hay 3 operaciones
            public double eval(Operation op, double x, double y) {
                return switch (op) {
                    case Add a        -> a.apply(x, y);
                    case Multiply m   -> m.apply(x, y);
                    case Constant c   -> c.value();
                    // Exhaustive: ningún otro caso posible
                };
            }
            """,
            q("¿Por qué usar records como subclases de sealed?",
                "Records son final, inmutables y concisos. Ideales para representar variantes de datos. Add() sin estado, Constant(double value) con estado. El compilador genera equals/hashCode/toString automáticamente. Juntos, sealed + record dan: типовая безопасность + immutabilidad + minimal código."),
            q("¿Sealed vs enum para qué?",
                "Enum: cuando tienes un conjunto CERRADO de instancias predefinidas (LUNES, MARTES...). Enum es singleton por valor. Sealed: cuando tienes un conjunto CERRADO de TIPOS (subclases), cada una con su propio estado. Enum no puede tener subclases; sealed sí puede tener jerarquía.")
        );

        // ===== SERVLETS Y FILTROS =====
        Concept servletsFiltros = concept("Servlets y Filtros", "servlets-filtros", Block.SPRING, 6,
            "Servlets son la base de las aplicaciones web Java. Reciben y responden peticiones HTTP. Los filtros interceptan peticiones antes de llegar al servlet, útiles para logging, seguridad y codificación.",
            null,
            cq("¿Qué es un Servlet?",
                "Componente Java que recibe peticiones HTTP y genera respuestas. Spring MVC se construye sobre el Servlet API (DispatcherServlet)."),
            cq("¿Qué es un Filter?",
                "Objeto que intercepta peticiones y respuestas. Se configura en el contenedor web y puede modificar o bloquear el paso al servlet."),
            cq("¿DispatcherServlet?",
                "Servlet central de Spring MVC. Recibe todas las peticiones, las enruta a controllers y coordina vistas o respuestas REST."));
        sc(servletsFiltros, "Servlet básico", "servlet-basico", 1,
            "Servlet clásico con anotaciones de Servlet 3.0.",
            """
            @WebServlet(name = "HolaServlet", urlPatterns = "/hola")
            public class HolaServlet extends HttpServlet {
                @Override
                protected void doGet(HttpServletRequest req, HttpServletResponse res)
                        throws ServletException, IOException {
                    res.setContentType("text/plain");
                    res.getWriter().write("Hola mundo");
                }
            }
            """,
            q("¿doGet vs doPost?",
                "doGet maneja peticiones GET. doPost maneja POST. Un servlet puede sobrescribir ambos según el método HTTP requerido."),
            q("¿Por qué Spring MVC es más popular que Servlets planos?",
                "Spring MVC abstrae el Servlet API, proporciona inyección de dependencias, data binding, validación, manejo de excepciones y testing mucho más sencillo."));
        sc(servletsFiltros, "Filtro", "filtro", 2,
            "Filtro para logging o configuración de cabeceras.",
            """
            @WebFilter("/*")
            public class LoggingFilter implements Filter {
                @Override
                public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
                        throws IOException, ServletException {
                    HttpServletRequest request = (HttpServletRequest) req;
                    System.out.println("Request: " + request.getRequestURI());
                    chain.doFilter(req, res);
                }
            }
            """,
            q("¿FilterChain?",
                "Permite pasar la petición al siguiente filtro o al servlet destino. Si no llamas a chain.doFilter(), la petición no continúa."),
            q("¿Filtro vs Interceptor de Spring?",
                "Filtro es del Servlet API y actúa antes de que Spring reciba la petición. Interceptor es de Spring MVC y tiene acceso al controller, modelo y excepciones."));

        // ===== GIT =====
        Concept git = concept("Git", "git", Block.TOOLS, 2,
            "Git es un sistema de control de versiones distribuido. Permite rastrear cambios, colaborar en equipo y gestionar diferentes líneas de desarrollo con ramas.",
            null,
            cq("¿Qué es Git?",
                "Sistema de control de versiones distribuido. Cada desarrollador tiene una copia completa del repositorio, incluyendo historia."),
            cq("¿Git vs GitHub?",
                "Git es la herramienta de control de versiones. GitHub es una plataforma web que aloja repositorios Git y añade funciones de colaboración (PRs, issues, actions)."),
            cq("¿Commit, branch, merge?",
                "Commit guarda un snapshot de cambios. Branch es una línea de desarrollo independiente. Merge integra una rama en otra."));
        sc(git, "Comandos esenciales", "git-comandos", 1,
            "Comandos básicos de uso diario.",
            """
            git clone <url>
            git pull
            git checkout -b feature/nueva-funcionalidad
            git add .
            git commit -m "feat: add user endpoint"
            git push -u origin feature/nueva-funcionalidad
            git checkout main
            git merge feature/nueva-funcionalidad
            """,
            q("¿git pull vs git fetch?",
                "fetch descarga cambios del remoto sin fusionarlos. pull hace fetch + merge. fetch es más seguro para revisar antes de integrar."),
            q("¿git merge vs git rebase?",
                "merge crea un commit de fusión y conserva historia. rebase aplica commits sobre la rama base, generando historia lineal pero reescribiendo commits. No uses rebase en ramas compartidas."));
        sc(git, "Resolución de conflictos", "git-conflictos", 2,
            "Cuando dos ramas modifican la misma línea, Git no puede fusionar automáticamente y pide intervención manual.",
            """
            <<<<<<< HEAD
            public void saludar() { return "hola"; }
            =======
            public void saludar() { return "hello"; }
            >>>>>>> feature/ingles
            """,
            q("¿Cómo resolver un conflicto?",
                "Edita el fichero conservando el código correcto, elimina los marcadores <<<<<<<, =======, >>>>>>>. Luego git add . y git commit."),
            q("¿git status durante conflicto?",
                "Muestra los ficheros en estado both modified. Ayuda a identificar qué archivos necesitan atención manual."));

        // ===== DOCKER =====
        Concept docker = concept("Docker", "docker", Block.TOOLS, 3,
            "Docker permite empaquetar aplicaciones con sus dependencias en contenedores ligeros y portables. Facilita despliegues consistentes entre desarrollo, testing y producción.",
            null,
            cq("¿Qué es un contenedor?",
                "Entorno aislado que ejecuta una aplicación con su sistema de archivos, dependencias y configuración. Comparte el kernel del host, por eso es más ligero que una VM."),
            cq("¿Imagen vs contenedor?",
                "Imagen es la plantilla inmutable. Contenedor es la instancia en ejecución de una imagen. Puedes crear múltiples contenedores de una misma imagen."),
            cq("¿Dockerfile?",
                "Fichero con instrucciones para construir una imagen: FROM, COPY, RUN, CMD, EXPOSE. Cada instrucción crea una capa."));
        sc(docker, "Dockerfile para Spring Boot", "dockerfile-spring", 1,
            "Dockerfile típico para empaquetar una app Spring Boot.",
            """
            FROM eclipse-temurin:21-jre-alpine
            WORKDIR /app
            COPY target/mi-app-1.0.0.jar app.jar
            EXPOSE 8080
            ENTRYPOINT ["java", "-jar", "app.jar"]
            """,
            q("¿Por qué usar JRE y no JDK en producción?",
                "La JRE es más ligera porque no incluye herramientas de compilación. Reduce superficie de ataque y tamaño de imagen."),
            q("¿ENTRYPOINT vs CMD?",
                "ENTRYPOINT define el comando principal y no se sobreescribe fácilmente. CMD proporciona argumentos por defecto. Con ENTRYPOINT fijas el ejecutable; con CMD puedes cambiarlo al ejecutar."));
        sc(docker, "Comandos Docker", "docker-comandos", 2,
            "Comandos básicos para gestionar imágenes y contenedores.",
            """
            docker build -t mi-app:1.0 .
            docker run -p 8080:8080 mi-app:1.0
            docker ps
            docker stop <container_id>
            docker logs <container_id>
            docker exec -it <container_id> /bin/sh
            """,
            q("¿docker run -p?",
                "Mapea puertos. -p 8080:8080 expone el puerto 8080 del contenedor en el puerto 8080 del host."),
            q("¿docker exec?",
                "Ejecuta un comando dentro de un contenedor en ejecución. Útil para depuración, inspeccionar ficheros o ejecutar shells."));

        // ===== SQL =====
        Concept sql = concept("SQL", "sql", Block.TOOLS, 4,
            "SQL (Structured Query Language) es el lenguaje estándar para gestionar y consultar bases de datos relacionales. Esencial para cualquier desarrollador backend.",
            null,
            cq("¿Qué es SQL?",
                "Lenguaje declarativo para bases de datos relacionales. Permite crear, leer, actualizar y eliminar datos (CRUD) y definir esquemas."),
            cq("¿DDL vs DML vs DCL?",
                "DDL (Data Definition Language): CREATE, ALTER, DROP. DML (Data Manipulation Language): SELECT, INSERT, UPDATE, DELETE. DCL: GRANT, REVOKE."),
            cq("¿Clave primaria vs foránea?",
                "Primaria identifica únicamente cada fila de una tabla. Foránea establece relación entre tablas y garantiza integridad referencial."));
        sc(sql, "Consultas básicas", "sql-basico", 1,
            "Operaciones CRUD y consultas simples.",
            """
            SELECT * FROM clientes WHERE activo = true ORDER BY nombre;
            INSERT INTO clientes (nombre, email) VALUES ('Ana', 'ana@example.com');
            UPDATE clientes SET email = 'nuevo@example.com' WHERE id = 1;
            DELETE FROM clientes WHERE id = 1;
            """,
            q("¿SELECT * es malo?",
                "En consultas de producción puede ser ineficiente porque trae columnas innecesarias y dificulta el uso de índices covering. Especifica solo las columnas que necesitas."),
            q("¿Diferencia WHERE vs HAVING?",
                "WHERE filtra filas antes de agrupar. HAVING filtra grupos después de GROUP BY. No puedes usar funciones agregadas en WHERE."));
        sc(sql, "JOINs", "sql-joins", 2,
            "Combinar datos de varias tablas es una de las operaciones más comunes y preguntadas.",
            """
            SELECT p.id, c.nombre, p.total
            FROM pedidos p
            JOIN clientes c ON p.cliente_id = c.id;

            -- LEFT JOIN incluye pedidos aunque no tengan cliente
            SELECT p.id, c.nombre
            FROM pedidos p
            LEFT JOIN clientes c ON p.cliente_id = c.id;
            """,
            q("¿INNER JOIN vs LEFT JOIN?",
                "INNER devuelve solo filas con coincidencia en ambas tablas. LEFT devuelve todas las filas de la izquierda y NULL donde no hay coincidencia."),
            q("¿Qué es un índice?",
                "Estructura de datos que acelera consultas a costa de espacio y rendimiento de escritura. Se crean sobre columnas frecuentemente consultadas o usadas en JOIN/WHERE."));

        // ===== MICROSERVICIOS =====
        Concept microservicios = concept("Microservicios", "microservicios", Block.SPRING_BOOT, 3,
            "Arquitectura que divide una aplicación en servicios pequeños, autónomos y desplegables de forma independiente. Cada servicio tiene su propia base de datos y responsabilidad de negocio.",
            null,
            cq("¿Qué son microservicios?",
                "Estilo arquitectónico donde una aplicación se compone de servicios pequeños, independientes y desplegables por separado. Cada uno se enfoca en una capacidad de negocio."),
            cq("¿Microservicios vs monolito?",
                "Monolito es más simple de desarrollar y desplegar inicialmente. Microservicios escalan mejor, permiten equipos independientes y tecnologías distintas, pero añaden complejidad operativa."),
            cq("¿Comunicación entre microservicios?",
                "Síncrona: HTTP/REST, gRPC. Asíncrona: mensajería con Kafka, RabbitMQ. Asíncrona reduce acoplamiento y mejora resiliencia."));
        sc(microservicios, "Ventajas y desventajas", "micro-pros-contras", 1,
            "No todos los proyectos necesitan microservicios. Es importante evaluar el contexto.",
            """
            Ventajas:
              - Escalado independiente por servicio
              - Equipos autónomos
              - Tolerancia a fallos parciales
              - Tecnologías heterogéneas

            Desventajas:
              - Complejidad operativa
              - Latencia de red
              - Consistencia eventual
              - Debugging distribuido
            """,
            q("¿Cuándo NO usar microservicios?",
                "Si el equipo es pequeño, el dominio poco claro o se prioriza velocidad de desarrollo. Muchos proyectos exitosos empiezan como monolitos modulares."),
            q("¿Service Discovery?",
                "Mecanismo para que los servicios se encuentren dinámicamente. En Spring Cloud se usa Eureka o Consul. Evita hardcodear URLs."));
        sc(microservicios, "API Gateway", "api-gateway", 2,
            "Punto de entrada único para clientes. Enruta peticiones a microservicios, maneja autenticación, rate limiting y SSL termination.",
            """
            Cliente -> API Gateway -> Servicio A
                                -> Servicio B
                                -> Servicio C
            """,
            q("¿Spring Cloud Gateway?",
                "Gateway de Spring Cloud basado en Spring WebFlux. Soporta routing, filtros, load balancing, circuit breakers e integración con OAuth2."),
            q("¿Por qué usar API Gateway?",
                "Oculta la complejidad interna de los microservicios, centraliza cross-cutting concerns y proporciona un único punto de contacto para clientes."));

        // ===== SPRING BOOT ACTUATOR =====
        Concept actuator = concept("Spring Boot Actuator", "spring-boot-actuator", Block.SPRING_BOOT, 4,
            "Actuator añade endpoints de producción a Spring Boot para monitorizar y gestionar la aplicación: health, metrics, info, loggers, etc.",
            null,
            cq("¿Qué es Spring Boot Actuator?",
                "Módulo de Spring Boot que expone endpoints operativos. Permite monitorizar health, métricas, configuración y gestionar loggers sin escribir código adicional."),
            cq("¿Endpoints importantes?",
                "/actuator/health: estado de la app. /actuator/metrics: métricas JVM y personalizadas. /actuator/info: información de build. /actuator/loggers: niveles de logging."),
            cq("¿Seguridad en Actuator?",
                "No expongas todos los endpoints públicamente. Restringe mediante Spring Security y expón solo health de forma pública si es necesario."));
        sc(actuator, "Configuración básica", "actuator-config", 1,
            "Cómo habilitar y exponer endpoints de Actuator.",
            """
            # application.properties
            management.endpoints.web.exposure.include=health,info,metrics,loggers
            management.endpoint.health.show-details=when_authorized

            # Acceso
            GET /actuator/health
            GET /actuator/metrics/jvm.memory.used
            """,
            q("¿Cómo añadir información a /info?",
                "Puedes configurar propiedades management.info.* o usar InfoContributors. También se integra con información del build si añades el plugin."),
            q("¿Health indicators personalizados?",
                "Implementas HealthIndicator y registras el bean. Útil para verificar conectividad con bases de datos, servicios externos o colas de mensajes."));

        // ===== OPENAPI / SWAGGER =====
        Concept openapi = concept("OpenAPI / Swagger", "openapi-swagger", Block.SPRING, 7,
            "OpenAPI es una especificación estándar para describir APIs REST. Swagger UI genera documentación interactiva a partir de esa especificación.",
            null,
            cq("¿Qué es OpenAPI?",
                "Especificación para documentar APIs REST: endpoints, métodos, parámetros, respuestas, esquemas. La versión actual es OpenAPI 3.0/3.1."),
            cq("¿Swagger vs OpenAPI?",
                "OpenAPI es la especificación. Swagger es el conjunto de herramientas (Swagger UI, Swagger Editor). Swagger UI genera una página web interactiva para probar la API."),
            cq("¿Springdoc?",
                "Librería que genera automáticamente la especificación OpenAPI a partir de controllers Spring Boot y expone /swagger-ui.html."));
        sc(openapi, "Springdoc OpenAPI", "springdoc", 1,
            "Configuración básica para añadir Swagger UI a Spring Boot.",
            """
            <dependency>
                <groupId>org.springdoc</groupId>
                <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
                <version>2.6.0</version>
            </dependency>

            // Acceso
            http://localhost:8080/swagger-ui.html
            http://localhost:8080/v3/api-docs
            """,
            q("¿Para qué sirve /v3/api-docs?",
                "Devuelve la especificación OpenAPI en formato JSON. Es consumido por Swagger UI y por herramientas de generación de clientes."),
            q("¿Se puede personalizar la documentación?",
                "Sí, con anotaciones como @Operation, @ApiResponse, @Schema, @Parameter sobre controllers y DTOs."));

        // ===== TRANSACCIONES =====
        Concept transacciones = concept("Transacciones @Transactional", "transacciones", Block.JPA_HIBERNATE, 3,
            "Una transacción agrupa operaciones de base de datos que deben ejecutarse de forma atómica: todas se confirman (commit) o ninguna (rollback). Spring gestiona transacciones con @Transactional.",
            null,
            cq("¿Qué es @Transactional?",
                "Anotación de Spring que declara que un método debe ejecutarse dentro de una transacción. Si ocurre una RuntimeException no controlada, se hace rollback automático."),
            cq("¿Propagation REQUIRED vs REQUIRES_NEW?",
                "REQUIRED (default): usa la transacción actual o crea una nueva. REQUIRES_NEW: suspende la transacción actual y crea una nueva; al finalizar, reanuda la anterior. Útil para operaciones independientes como logs o auditoría."),
            cq("¿Qué excepciones causan rollback?",
                "Por defecto solo RuntimeException y Error causan rollback. Las checked exceptions no causan rollback a menos que configures rollbackFor o noRollbackFor."));
        sc(transacciones, "Configuración básica", "transactional-basico", 1,
            "Uso típico de @Transactional en capa de servicio.",
            """
            @Service
            @Transactional
            public class PedidoService {
                private final PedidoRepository pedidoRepo;
                private final StockService stockService;

                public Pedido crearPedido(PedidoDTO dto) {
                    Pedido pedido = pedidoRepo.save(new Pedido(dto));
                    stockService.descontar(dto.productoId(), dto.cantidad());
                    return pedido;
                }
            }
            """,
            q("¿@Transactional en clase o método?",
                "En clase aplica a todos los métodos públicos. En método anula la configuración de clase. Es buena práctica ponerlo a nivel de servicio."),
            q("¿Rollback manual?",
                "Puedes lanzar RuntimeException o llamar a TransactionAspectSupport.currentTransactionStatus().setRollbackOnly() para marcar la transacción actual como rollback-only."));
        sc(transacciones, "Niveles de aislamiento", "isolation-levels", 2,
            "Los isolation levels controlan cuánto se ven las transacciones entre sí. De menor a mayor aislamiento: READ_UNCOMMITTED, READ_COMMITTED, REPEATABLE_READ, SERIALIZABLE.",
            """
            @Transactional(isolation = Isolation.READ_COMMITTED)
            public void procesarPago() { ... }
            """,
            q("¿Dirty read, non-repeatable read, phantom read?",
                "Dirty read: leer datos no confirmados. Non-repeatable read: leer mismo dato dos veces y obtener valores distintos. Phantom read: misma consulta devuelve filas distintas. A mayor aislamiento, menos problemas pero más bloqueos."),
            q("¿Isolation default?",
                "DEFAULT delega al nivel por defecto de la base de datos. PostgreSQL usa READ COMMITTED por defecto."));

        // ===== JDBC =====
        Concept jdbc = concept("JDBC", "jdbc", Block.JPA_HIBERNATE, 4,
            "JDBC (Java Database Connectivity) es la API de bajo nivel para conectar Java con bases de datos relacionales. JPA y Hibernate se construyen sobre JDBC.",
            null,
            cq("¿Qué es JDBC?",
                "API estándar de Java para ejecutar SQL contra bases de datos. Usa Connection, Statement, PreparedStatement y ResultSet. Requiere manejo manual de recursos y excepciones."),
            cq("¿PreparedStatement vs Statement?",
                "PreparedStatement precompila la consulta y evita SQL injection al parametrizar valores. Statement ejecuta SQL directo y es inseguro para inputs dinámicos."),
            cq("¿Por qué usar JPA sobre JDBC?",
                "JPA reduce boilerplate, mapea objetos a tablas automáticamente y abstrae diferencias entre bases de datos. JDBC ofrece más control para queries complejas o masivas."));
        sc(jdbc, "CRUD con JDBC", "jdbc-crud", 1,
            "Ejemplo básico de consulta parametrizada con try-with-resources.",
            """
            public Optional<Cliente> findById(Long id) {
                String sql = "SELECT id, nombre FROM clientes WHERE id = ?";
                try (Connection conn = dataSource.getConnection();
                     PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setLong(1, id);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            return Optional.of(new Cliente(
                                rs.getLong("id"),
                                rs.getString("nombre")));
                        }
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                return Optional.empty();
            }
            """,
            q("¿DataSource?",
                "Interfaz que proporciona conexiones a la base de datos. Spring configura automáticamente un DataSource a partir de application.properties. HikariCP es el pool por defecto."),
            q("¿Por qué try-with-resources?",
                "Connection, PreparedStatement y ResultSet deben cerrarse. try-with-resources cierra automáticamente en orden inverso, evitando fugas de recursos."));
        sc(jdbc, "JdbcTemplate", "jdbc-template", 2,
            "Spring JdbcTemplate simplifica JDBC manejando conexiones, excepciones y mapeo de resultados.",
            """
            @Repository
            public class ClienteJdbcRepository {
                private final JdbcTemplate jdbcTemplate;

                public ClienteJdbcRepository(JdbcTemplate jdbcTemplate) {
                    this.jdbcTemplate = jdbcTemplate;
                }

                public List<Cliente> findAll() {
                    return jdbcTemplate.query(
                        "SELECT id, nombre FROM clientes",
                        (rs, rowNum) -> new Cliente(
                            rs.getLong("id"),
                            rs.getString("nombre")));
                }
            }
            """,
            q("¿JdbcTemplate vs JPA?",
                "JdbcTemplate es más verboso que JPA pero da control total sobre el SQL. Úsalo para queries complejas, reportes o cuando el mapeo relacional no encaja bien con JPA."),
            q("¿RowMapper?",
                "Interfaz que mapea cada fila de ResultSet a un objeto. Puedes usar lambda o implementarla como clase separada si el mapeo es complejo."));

        // ===== SPRING SECURITY JWT =====
        Concept jwtSecurity = concept("JWT con Spring Security", "jwt-security", Block.SPRING, 8,
            "JWT (JSON Web Token) es un estándar para tokens firmados que contienen claims. Se usa comúnmente para autenticación stateless en APIs REST.",
            null,
            cq("¿Qué es JWT?",
                "Token firmado digitalmente compuesto por header, payload (claims) y signature. Permite autenticación stateless: el cliente envía el token en cada petición."),
            cq("¿Estructura de JWT?",
                "header.payload.signature. Header define algoritmo. Payload contiene claims como sub, exp, roles. Signature valida que no fue alterado."),
            cq("¿Dónde enviar el JWT?",
                "En la cabecera Authorization con el prefijo Bearer: Authorization: Bearer <token>."));
        sc(jwtSecurity, "Flujo de autenticación", "jwt-flujo", 1,
            "Pasos típicos de autenticación con JWT en Spring Security.",
            """
            1. Cliente envía credenciales POST /login
            2. Servidor valida y genera JWT firmado
            3. Cliente almacena token (localStorage / cookie httpOnly)
            4. Cliente envía token en cada request: Authorization: Bearer <token>
            5. Filtro de Spring Security valida firma y extrae roles
            """,
            q("¿JWT vs sesión?",
                "JWT es stateless: no requiere almacenar sesión en servidor. Sesión tradicional guarda estado en servidor (memory, Redis, DB). JWT escala mejor horizontalmente."),
            q("¿Riesgos de JWT?",
                "Tokens no se pueden revocar fácilmente hasta que expiran. Si se filtran, un atacante puede usarlos. Usa HTTPS, expiraciones cortas y refresh tokens."));
        sc(jwtSecurity, "Filtro JWT en Spring", "jwt-filtro", 2,
            "Configuración típica con un OncePerRequestFilter que valida el token.",
            """
            @Component
            public class JwtAuthFilter extends OncePerRequestFilter {
                @Override
                protected void doFilterInternal(HttpServletRequest req,
                                                  HttpServletResponse res,
                                                  FilterChain chain)
                        throws ServletException, IOException {
                    String header = req.getHeader("Authorization");
                    if (header != null && header.startsWith("Bearer ")) {
                        String token = header.substring(7);
                        // validar firma y extraer usuario/roles
                        // establecer Authentication en SecurityContext
                    }
                    chain.doFilter(req, res);
                }
            }
            """,
            q("¿OncePerRequestFilter?",
                "Garantiza que el filtro se ejecuta una sola vez por petición, incluso con forwards o includes. Es la base de filtros JWT en Spring."),
            q("¿SecurityContextHolder?",
                "Almacena la autenticación del usuario actual durante la petición. Los controllers pueden obtener el usuario con @AuthenticationPrincipal."));

        // ===== WEBCLIENT / RESTTEMPLATE =====
        Concept httpClient = concept("WebClient y RestTemplate", "webclient-resttemplate", Block.SPRING_BOOT, 5,
            "Spring ofrece clientes HTTP para consumir APIs externas. RestTemplate es síncrono y está en modo maintenance. WebClient es la opción moderna, reactiva y no bloqueante.",
            null,
            cq("¿WebClient vs RestTemplate?",
                "WebClient es no bloqueante, reactivo y concurrente por defecto. RestTemplate es bloqueante y ya no recibe nuevas funcionalidades. En proyectos nuevos se recomienda WebClient."),
            cq("¿Cómo crear WebClient?",
                "Puedes usar WebClient.builder() o inyectar WebClient.Builder configurado como bean. Permite configurar baseUrl, timeouts, codecs y filtros."),
            cq("¿RestTemplate está obsoleto?",
                "No está deprecado oficialmente, pero está en maintenance mode. Spring recomienda WebClient para nuevo código."));
        sc(httpClient, "WebClient básico", "webclient-basico", 1,
            "Ejemplo de consumo de API externa con WebClient.",
            """
            @Service
            public class PokemonService {
                private final WebClient webClient;

                public PokemonService(WebClient.Builder builder) {
                    this.webClient = builder.baseUrl("https://pokeapi.co/api/v2").build();
                }

                public Mono<Pokemon> findByName(String name) {
                    return webClient.get()
                        .uri("/pokemon/{name}", name)
                        .retrieve()
                        .bodyToMono(Pokemon.class);
                }
            }
            """,
            q("¿Mono vs Flux?",
                "Mono representa 0 o 1 elemento. Flux representa 0 a N elementos. Ambos son tipos reactivos de Project Reactor."),
            q("¿retrieve vs exchange?",
                "retrieve es más simple y maneja errores HTTP automáticamente. exchange da acceso completo a ClientResponse pero requiere liberar recursos manualmente."));
        sc(httpClient, "RestTemplate básico", "resttemplate-basico", 2,
            "Ejemplo clásico con RestTemplate para comparar.",
            """
            @Service
            public class PokemonService {
                private final RestTemplate restTemplate = new RestTemplate();

                public Pokemon findByName(String name) {
                    return restTemplate.getForObject(
                        "https://pokeapi.co/api/v2/pokemon/{name}",
                        Pokemon.class,
                        name);
                }
            }
            """,
            q("¿RestTemplate bloquea el hilo?",
                "Sí. La llamada es bloqueante: el hilo espera la respuesta HTTP. Bajo alta concurrencia esto consume más recursos que WebClient."),
            q("¿Cuándo sigue siendo válido RestTemplate?",
                "En aplicaciones síncronas simples, migraciones graduales o cuando el equipo no domina programación reactiva. Pero no se recomienda para nuevo código."));

        // ===== PROFILES Y CONFIGURACIÓN EXTERNA =====
        Concept profilesConfig = concept("Profiles y Configuración Externa", "profiles-config", Block.SPRING_BOOT, 6,
            "Spring Boot permite externalizar configuración y activar perfiles para adaptar la aplicación a diferentes entornos sin cambiar código.",
            null,
            cq("¿Qué son los profiles?",
                "Mecanismo para agrupar beans y configuración según el entorno: dev, test, prod. Se activan con @Profile o spring.profiles.active."),
            cq("¿Orden de prioridad de propiedades?",
                "1. Línea de comandos. 2. Variables de entorno. 3. application-{profile}.properties. 4. application.properties. 5. @PropertySource. Ver documentación completa en Spring Externalized Configuration."),
            cq("¿application.properties vs application.yml?",
                "Ambos sirven. YAML es más legible para configuraciones jerárquicas. Properties es más simple y común."));
        sc(profilesConfig, "Uso de profiles", "profiles-uso", 1,
            "Activar y definir beans por perfil.",
            """
            # application.properties
            spring.profiles.active=dev

            # application-dev.properties
            server.port=8081
            logging.level.org.springframework=DEBUG

            // Bean solo en dev
            @Bean
            @Profile("dev")
            public DataSource h2DataSource() { ... }
            """,
            q("¿@Profile a nivel de @Configuration?",
                "Sí. Puedes anotar toda una clase @Configuration con @Profile para cargarla solo en un entorno específico."),
            q("¿Múltiples profiles?",
                "Se pueden activar varios separados por comas: spring.profiles.active=dev,local. Los archivos application-{profile} se cargan en orden."));
        sc(profilesConfig, "Variables de entorno", "env-vars", 2,
            "Spring Boot mapea automáticamente variables de entorno a propiedades. Es clave para despliegues en la nube.",
            """
            # application.properties referencia env var con valor por defecto
            spring.datasource.url=${DATABASE_URL:jdbc:h2:mem:testdb}

            # Render / Heroku pasan DATABASE_URL como variable de entorno
            """,
            q("¿Por qué externalizar configuración?",
                "Permite usar el mismo artefacto (jar) en todos los entornos. Las credenciales y URLs no viajan en el código fuente."),
            q("¿@Value?",
                "Permite inyectar propiedades en campos: @Value(\"${app.nombre}\") private String nombre;. Para propiedades obligatorias considera @ConfigurationProperties."));

        // ===== SPRING CACHE =====
        Concept springCache = concept("Spring Cache", "spring-cache", Block.SPRING_BOOT, 7,
            "Spring Cache abstrae el uso de cachés. Con anotaciones como @Cacheable, @CachePut y @CacheEvict puedes cachear resultados de métodos sin acoplar el código a una implementación concreta.",
            null,
            cq("¿Qué es @Cacheable?",
                "Anotación que indica que el resultado de un método debe almacenarse en caché. En siguientes llamadas con la misma clave se devuelve el valor cacheado sin ejecutar el método."),
            cq("¿Implementaciones de caché?",
                "ConcurrentMapCache (por defecto en memoria), Caffeine, EhCache, Redis. Spring Boot autoconfigura la que elijas añadiendo la dependencia correspondiente."),
            cq("¿CacheEvict vs CachePut?",
                "@CacheEvict elimina entradas de la caché. @CachePut actualiza la caché con el resultado del método sin evitar su ejecución."));
        sc(springCache, "Caché básica", "cache-basico", 1,
            "Ejemplo de cacheo de resultado con clave basada en parámetros.",
            """
            @Service
            public class ProductoService {

                @Cacheable(value = "productos", key = "#id")
                public Producto findById(Long id) {
                    return productoRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException("Producto no encontrado"));
                }

                @CacheEvict(value = "productos", key = "#id")
                public void deleteById(Long id) {
                    productoRepository.deleteById(id);
                }
            }
            """,
            q("¿Cómo generar la clave de caché?",
                "Por defecto usa todos los parámetros. Puedes personalizar con SpEL: key = \"#id\", key = \"#user.email\" o usar keyGenerator personalizado."),
            q("¿Qué pasa si el método lanza excepción?",
                "No se cachea nada. El error se propaga normalmente."));
        sc(springCache, "Caffeine / Redis", "cache-providers", 2,
            "Dependencias para cachés de producción.",
            """
            <!-- Caffeine (caché local en memoria) -->
            <dependency>
                <groupId>com.github.ben-manes.caffeine</groupId>
                <artifactId>caffeine</artifactId>
            </dependency>

            <!-- Redis (caché distribuida) -->
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-data-redis</artifactId>
            </dependency>
            """,
            q("¿Caffeine vs Redis?",
                "Caffeine es caché local en memoria, muy rápida pero no compartida entre instancias. Redis es distribuida, ideal para múltiples réplicas de la aplicación."),
            q("¿TTL en caché?",
                "Time To Live: tiempo máximo que una entrada permanece en caché. Se configura en el proveedor, por ejemplo spring.cache.redis.time-to-live=10m."));

        // ===== MENSAJERÍA =====
        Concept mensajeria = concept("Mensajería con Spring", "mensajeria", Block.SPRING_BOOT, 8,
            "La mensajería asíncrona desacopla servicios y mejora resiliencia. Spring ofrece abstracciones para JMS, RabbitMQ, Kafka y eventos internos.",
            null,
            cq("¿Mensajería síncrona vs asíncrona?",
                "Síncrona: el emisor espera respuesta (HTTP). Asíncrona: el emisor publica y continúa; el receptor procesa cuando puede. Reduce acoplamiento y mejora tolerancia a fallos."),
            cq("¿RabbitMQ vs Kafka?",
                "RabbitMQ es un broker de mensajería tradicional con colas y exchanges. Kafka es una plataforma de streaming distribuida con topics y particiones. Kafka escala mejor para grandes volúmenes y procesamiento de eventos."),
            cq("¿JMS vs AMQP?",
                "JMS es la API Java para mensajería (ActiveMQ). AMQP es un protocolo abierto (RabbitMQ). Spring Template abstrae ambos con JmsTemplate y RabbitTemplate."));
        sc(mensajeria, "Kafka básico", "kafka-basico", 1,
            "Conceptos fundamentales de Apache Kafka.",
            """
            // Productor
            @Service
            public class PedidoProducer {
                private final KafkaTemplate<String, Pedido> kafkaTemplate;

                public void enviar(Pedido pedido) {
                    kafkaTemplate.send("pedidos", pedido.getId().toString(), pedido);
                }
            }

            // Consumidor
            @KafkaListener(topics = "pedidos", groupId = "grupo-pedidos")
            public void recibir(Pedido pedido) { ... }
            """,
            q("¿Topic, partition, offset?",
                "Topic es la categoría de mensajes. Partition divide el topic para paralelismo. Offset es la posición de un mensaje dentro de una partición."),
            q("¿Consumer group?",
                "Grupo de consumidores que leen de un topic. Cada partición es consumida por un solo miembro del grupo, permitiendo escalado horizontal."));
        sc(mensajeria, "RabbitMQ básico", "rabbitmq-basico", 2,
            "Conceptos de RabbitMQ: exchange, queue y routing key.",
            """
            @Service
            public class NotificacionProducer {
                private final RabbitTemplate rabbitTemplate;

                public void enviar(Notificacion n) {
                    rabbitTemplate.convertAndSend("exchange.notificaciones",
                        "notificaciones.email", n);
                }
            }

            @RabbitListener(queues = "cola.email")
            public void recibir(Notificacion n) { ... }
            """,
            q("¿Exchange types?",
                "Direct: routing key exacta. Topic: patrones de routing key. Fanout: envía a todas las colas vinculadas. Headers: filtra por cabeceras."),
            q("¿Ventaja de RabbitMQ?",
                "Routing flexible, confirmaciones de entrega, colas duraderas, TTL y dead letter exchanges. Muy maduro para mensajería empresarial."));

        // ===== LOGGING =====
        Concept logging = concept("Logging", "logging", Block.TOOLS, 5,
            "El logging permite registrar eventos de la aplicación para debugging, monitorización y auditoría. En Java se usa SLF4J como fachada y Logback/Log4j2 como implementación.",
            null,
            cq("¿SLF4J?",
                "Simple Logging Facade for Java. Interfaz abstracta que permite cambiar la implementación de logging sin modificar el código. Es el estándar en Spring Boot."),
            cq("¿Niveles de log?",
                "TRACE < DEBUG < INFO < WARN < ERROR. Cada nivel indica severidad. En producción suele usarse INFO o WARN; en desarrollo DEBUG."),
            cq("¿Logback vs Log4j2?",
                "Logback es el default de Spring Boot. Log4j2 ofrece mejor rendimiento y más funcionalidades avanzadas. Ambos implementan SLF4J."));
        sc(logging, "SLF4J con Lombok", "slf4j-lombok", 1,
            "Ejemplo de logging con SLF4J. En proyectos con Lombok se usa @Slf4j, pero aquí mostramos la forma explícita.",
            """
            import org.slf4j.Logger;
            import org.slf4j.LoggerFactory;

            public class PedidoService {
                private static final Logger log = LoggerFactory.getLogger(PedidoService.class);

                public void procesar(Long id) {
                    log.info("Procesando pedido {}", id);
                    log.debug("Detalle del pedido: {}", pedido);
                    log.error("Error al procesar pedido {}", id, ex);
                }
            }
            """,
            q("¿Por qué no usar System.out.println?",
                "No tiene niveles, no se puede desactivar por paquete, no añade timestamps ni contexto, y ralentiza la aplicación si escribe a consola sincrónicamente."),
            q("¿Placeholders {}?",
                "SLF4J usa {} para interpolar argumentos. No concatena strings si el nivel no está activo, mejorando rendimiento. Ejemplo: log.debug(\"valor {}\", costoso()) evita llamar a costoso() si DEBUG está desactivado."));
        sc(logging, "Configuración de Logback", "logback-config", 2,
            "Configuración típica en logback-spring.xml.",
            """
            <configuration>
                <appender name="CONSOLA" class="ch.qos.logback.core.ConsoleAppender">
                    <encoder>
                        <pattern>%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n</pattern>
                    </encoder>
                </appender>

                <logger name="com.javaconcepts" level="DEBUG"/>
                <root level="INFO">
                    <appender-ref ref="CONSOLA"/>
                </root>
            </configuration>
            """,
            q("¿logback.xml vs logback-spring.xml?",
                "logback-spring.xml permite usar perfiles de Spring (<springProfile>) y propiedades. Se carga después de application.properties."),
            q("¿Structured logging?",
                "Logs en formato JSON facilitan análisis con ELK, Loki o CloudWatch. Se configura con encoders específicos como logstash-logback-encoder."));

        // ===== GRADLE =====
        Concept gradle = concept("Gradle", "gradle", Block.TOOLS, 6,
            "Gradle es una alternativa moderna a Maven para construir proyectos Java. Usa Groovy o Kotlin DSL y un modelo de tareas incremental.",
            null,
            cq("¿Gradle vs Maven?",
                "Gradle usa scripts declarativos o programáticos (build.gradle.kts), tiene mejor rendimiento gracias a build cache y task incremental, y es más flexible. Maven es más estándar y estructurado con XML."),
            cq("¿build.gradle.kts?",
                "Script de build escrito en Kotlin. Define plugins, dependencies, repositories, tasks y configuración del proyecto."),
            cq("¿Tasks en Gradle?",
                "Unidades de trabajo como compileJava, test, bootRun, jar. Puedes crear tasks personalizadas y encadenarlas."));
        sc(gradle, "build.gradle.kts básico", "gradle-basico", 1,
            "Ejemplo mínimo de build Gradle con Kotlin DSL para Spring Boot.",
            """
            plugins {
                java
                id("org.springframework.boot") version "3.3.5"
                id("io.spring.dependency-management") version "1.1.6"
            }

            group = "com.ejemplo"
            version = "1.0.0"

            java {
                sourceCompatibility = JavaVersion.VERSION_21
            }

            repositories {
                mavenCentral()
            }

            dependencies {
                implementation("org.springframework.boot:spring-boot-starter-web")
                testImplementation("org.springframework.boot:spring-boot-starter-test")
            }
            """,
            q("¿implementation vs api vs compileOnly?",
                "implementation: dependencia interna no expuesta. api: se expone a consumidores del módulo. compileOnly: necesaria para compilar, no en runtime. Equivalente a provided en Maven."),
            q("¿Gradle wrapper?",
                "Scripts gradlew y gradlew.bat que permiten ejecutar la versión correcta de Gradle sin instalarla globalmente. Se usa en CI/CD."));
        sc(gradle, "Comandos Gradle", "gradle-comandos", 2,
            "Comandos comunes.",
            """
            ./gradlew build
            ./gradlew test
            ./gradlew bootRun
            ./gradlew bootJar
            ./gradlew clean
            """,
            q("¿gradlew build vs bootJar?",
                "build ejecuta tests y genera artefactos. bootJar crea el jar ejecutable de Spring Boot. bootRun lanza la aplicación."),
            q("¿Por qué Gradle es más rápido?",
                "Build cache reusa outputs de tareas anteriores. Task incremental solo reejecuta lo necesario. Daemon mantiene un proceso Gradle vivo entre builds."));

        // Guardar/actualizar conceptos raíz de forma idempotente
        List<Concept> allRoots = List.of(
            clase, objeto, herencia, polimorfismo, encapsulamiento,
            interfaceConcept, abstractClass, excepciones, genericos,
            colecciones, streams,
            multithreading, sincronizacion, concurrenciaAvanzada,
            plataformaJava,
            programacionFuncional, designPatterns, jvmGc, serializacion,
            springFramework, ioc, springMvc, springSecurity, jwtSecurity, restApi, openapi, servletsFiltros,
            springBoot, springBootTesting, microservicios, actuator,
            httpClient, profilesConfig, springCache, mensajeria,
            jpa, hibernate, transacciones, jdbc,
            testing,
            solid, cleanCode,
            maven, git, docker, sql, logging, gradle
        );

        // Carga masiva de datos existentes para reducir consultas N+1
        List<Concept> existingRoots = conceptRepository.findAllByParentIsNullOrderByOrderIndexAsc();
        Map<String, Concept> existingBySlug = new HashMap<>();
        Map<Long, Set<String>> questionTextsByConcept = new HashMap<>();
        Map<Long, Set<String>> subConceptSlugsByConcept = new HashMap<>();

        for (Concept root : existingRoots) {
            existingBySlug.put(root.getSlug(), root);
            questionTextsByConcept.put(root.getId(),
                root.getQuestions().stream()
                    .map(ConceptQuestion::getQuestion)
                    .collect(HashSet::new, Set::add, Set::addAll));
            subConceptSlugsByConcept.put(root.getId(),
                root.getSubConcepts().stream()
                    .map(SubConcept::getSlug)
                    .collect(HashSet::new, Set::add, Set::addAll));
        }

        List<Concept> toSave = new ArrayList<>();
        for (Concept newRoot : allRoots) {
            Concept existing = existingBySlug.get(newRoot.getSlug());
            if (existing == null) {
                toSave.add(newRoot);
                continue;
            }

            existing.setDescription(newRoot.getDescription());
            existing.setCodeExample(newRoot.getCodeExample());
            existing.setBlock(newRoot.getBlock());

            Set<String> existingQuestions = questionTextsByConcept.getOrDefault(existing.getId(), Set.of());
            for (ConceptQuestion newQ : newRoot.getQuestions()) {
                if (!existingQuestions.contains(newQ.getQuestion())) {
                    existing.addQuestion(new ConceptQuestion(newQ.getQuestion(), newQ.getAnswer()));
                }
            }

            Set<String> existingSubConcepts = subConceptSlugsByConcept.getOrDefault(existing.getId(), Set.of());
            for (SubConcept newSc : newRoot.getSubConcepts()) {
                if (!existingSubConcepts.contains(newSc.getSlug())) {
                    SubConcept sc = new SubConcept(
                            newSc.getTitle(), newSc.getSlug(), newSc.getOrderIndex(),
                            newSc.getDescription(), newSc.getCodeExample());
                    for (SubConceptQuestion newSq : newSc.getQuestions()) {
                        sc.addQuestion(new SubConceptQuestion(newSq.getQuestion(), newSq.getAnswer()));
                    }
                    existing.addSubConcept(sc);
                }
            }
            toSave.add(existing);
        }

        conceptRepository.saveAll(toSave);
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
