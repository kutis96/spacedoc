package in.spcct.spacedoc.exec;

import in.spcct.spacedoc.cdi.Registry;
import in.spcct.spacedoc.common.module.Module;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Main {

    public static void main(String[] args) throws Exception {

        Setup.registerAll();

        //1) Find module or print help
        //2) Strip initial module argument
        //3) Dispatch remaining arguments to module

        String moduleName;
        if (args == null || args.length == 0) {
            //print help, somehow
            moduleName = "help";
        } else {
            moduleName = args[0];
        }

        Module module = lookupModule(moduleName);

        module.run(stripInitialArgument(args));

    }

    private static String[] stripInitialArgument(String[] args) {
        if (args == null || args.length < 1)
            return new String[]{};

        return Arrays.copyOfRange(args, 1, args.length);
    }

    private static List<Module> getModules() {
        return Registry.lookupAll(Module.class, 0);
    }

    private static Module lookupModule(String moduleName) {
        Optional<Module> maybeModule = getModules()
                .stream()
                .filter(m -> m.getLongName().equalsIgnoreCase(moduleName) || m.getShortName().equalsIgnoreCase(moduleName))
                .findFirst();

        return maybeModule.orElseGet(Main::getHelpModule);
    }

    private static Module getHelpModule() {
        return new Module() {
            @Override
            public String getLongName() {
                return "help";
            }

            @Override
            public String getDescription() {
                return "Prints this help";
            }

            @Override
            public void run(String[] args) {
                //TODO: Render help, somehow
                System.out.println("Usage: spacedoc [module] <arguments...>");
                System.out.println("Usage: spacedoc [module] help");
                System.out.println("Modules:");
                System.out.printf(
                        "%16s : %s\n", "(Module)", "(Description)"
                );
                for (Module module : getModules()) {
                    System.out.printf(
                            "%16s : %s\n", module.getLongName(), module.getDescription()
                    );
                }
            }
        };
    }
}
