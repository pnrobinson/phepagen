package org.monarchinitiative.phenogen;


import org.monarchinitiative.phenogen.cmd.PhenoGenCommand;
import picocli.CommandLine;

import java.net.URL;
import java.util.Properties;
import java.util.concurrent.Callable;

/**
 * An app to generate phenopackets for testing. Phenopackets are written to an output directory called data
 */
//@Command(versionProvider = VersionProviderDemo1.PropertiesVersionProvider.class)

@CommandLine.Command(name = "PhePaGen", mixinStandardHelpOptions = true,
        description = "Phenopacket generation app.")
public class Main implements Callable<Integer>  {

    public static void main(String [] args){
        if (args.length == 0) {
            // if the user doesn't pass any command or option, add -h to show help
            args = new String[]{"-h"};
        }
        CommandLine cline = new CommandLine(new Main())
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
