package sk.leziemevpezinku.spring.model;

public class Views {

    public static class Public { }

    public static class Internal extends Public { }

    public static class EventPublic extends Public { }

    public static class EventInternal extends Internal { }

    public static class EventTerm extends Internal { }
}
