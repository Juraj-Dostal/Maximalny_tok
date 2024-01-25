
public class MainClass {
    public static void main(String[] args) {
        String name = "Tok_mini2.hrn";
        Input input = new Input();
        input.readData(name);

        System.out.println("Hran: " + input.getPocetHran());
        System.out.println("Vrcholov: " + input.getPocetVrchol());

        var maxTok = new MaximalnyTok(input);


    }
}
