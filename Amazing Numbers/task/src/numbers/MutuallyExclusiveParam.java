package numbers;

public class MutuallyExclusiveParam extends Exception {

    public MutuallyExclusiveParam(Main.Property p1, Main.Property p2) {
        System.out.println("The request contains mutually exclusive properties: ["
                + p1.name() + ", "
                + p2.name()
                + "]"
                + System.lineSeparator()
                + "There are no numbers with these properties."
        );
    }
}
