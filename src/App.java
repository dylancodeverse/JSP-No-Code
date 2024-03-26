import overriden.MainProcessNew;
import process.MainProcess;

public class App {
    public static void main(String[] args) throws Exception {
        new MainProcessNew().scriptProcess(
                "C:\\Users\\MISA\\Desktop\\Workspace\\S6\\Mr-Naina\\GenesisForJSP\\test\\fichierdegeneration.tmpl",
                "jsp-edit", new String[] { "student" });
        // new MainProcess().scriptProcessOnePage(
        //     "C:\\Users\\MISA\\Desktop\\Workspace\\S6\\Mr-Naina\\GenesisForJSP\\test\\fichierdegeneration.tmpl",
        //     "jsp-nav", new String[] { "student","promotion" }, "C:/Users/MISA/Desktop/Workspace/S6/Mr-Naina/GenesisForJSP/test/fixTemplate/Nav.jsp" ,"#here#","nav");

    }
}
