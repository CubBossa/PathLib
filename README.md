# PathLibrary

## What is it used for?

This path library helps to calculate the shortest path on a graph. It is designed to convert another data container into
a graph easily, like Vectors or serializable Nodes.

## How to use?

To sort nodes, unique keys are used. In this simple example, Nodes are strings and
the string also represents the key. Every Edge has a length of one.

Instead of Strings, every possible object can be converted and used for path calculation.

```Java
public class Example {
    public static void main(String[] args) {
        Set<String> nodes = new HashSet<>();
        nodes.add("Point A");
        nodes.add("Point B");
        nodes.add("Point C");
        nodes.add("Point D");

        Set<Pair<String, String>> edges = new HashSet<>();
        edges.add(Pair.of("Point A", "Point B"));
        edges.add(Pair.of("Point B", "Point C"));
        edges.add(Pair.of("Point B", "Point D"));
        edges.add(Pair.of("Point C", "Point D"));

        // The string also serves as key, so we can use s -> s for mapping
        // The distance function returns 1f, so all connected nodes are equidistant
        Graph<String> graph = new Graph<>(nodes, edges, s -> s, s -> s, (s, s2) -> 1f);

        String start = "Point A";
        String target = "Point D";
        System.out.println("\n\nSimple Example");
        // Returns a list of strings -> the keys converted back to our Graph Type, which is also a string
        graph.getShortestPath(start, target).forEach(s -> System.out.print(s + " -> "));
    }
}
```

In the following example, named 3-dimensional Vectors are used instead of strings. In this case, an individual distance function can be used.

```Java
public class Example {
    public static void main(String[] args) {

        Vector a = new Vector("a", 1, 1, 1);
        Vector b = new Vector("b", 1, 2.5f, -1);
        Vector c = new Vector("c", 3, 0, 1);
        Vector d = new Vector("d", -1, 0, -1);

        Set<Vector> nodes = new HashSet<>();
        nodes.add(a);
        nodes.add(b);
        nodes.add(c);
        nodes.add(d);

        Set<Pair<Vector, Vector>> edges = new HashSet<>();
        edges.add(Pair.of(a, b));
        edges.add(Pair.of(b, a));
        edges.add(Pair.of(b, c));
        edges.add(Pair.of(b, d));
        edges.add(Pair.of(c, d));

        // The name of the vector class serves as key, to get the vector object from the key, a java stream is used in this example.
        Graph<Vector> graph = new Graph<>(nodes, edges, Vector::getName,
                string -> nodes.stream().filter(vector -> vector.getName().equals(string)).findFirst().orElse(null), Vector::distance);

        System.out.println("\n\nVector Example");
        graph.getShortestPath(a, d).forEach(s -> System.out.print(s.getName() + " -> "));
    }
}
```