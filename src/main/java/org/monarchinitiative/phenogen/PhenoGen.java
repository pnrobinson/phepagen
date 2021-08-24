package org.monarchinitiative.phenogen;


import org.monarchinitiative.phenogen.cmd.PhenoGenCommand;
import picocli.CommandLine;

import java.util.concurrent.Callable;

/**
 * An app to generate phenopackets for testing. Phenopackets are written to an output directory called data
 */
@CommandLine.Command(name = "PhenoGen", mixinStandardHelpOptions = true, version = "0.1.1",
        description = "Phenopacket generation app.")
public class PhenoGen implements Callable<Integer>  {


    public static void main(String [] args){
        if (args.length == 0) {
            // if the user doesn't pass any command or option, add -h to show help
            args = new String[]{"-h"};
        }
        CommandLine cline = new CommandLine(new PhenoGen())
                .addSubcommand("create", new PhenoGenCommand());
        cline.setToggleBooleanFlags(false);
        int exitCode = cline.execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() {
        // work done in subcommands
        return 0;
    }
}
