package org.monarchinitiative.csv2pp;


import org.monarchinitiative.csv2pp.cmd.Csv2PhenopacketCommand;
import org.monarchinitiative.csv2pp.cmd.IdCommand;
import org.monarchinitiative.csv2pp.cmd.ParseCsvCommand;
import picocli.CommandLine;

import java.util.concurrent.Callable;

public class Csv2Phenopacket implements Callable<Integer>  {


    public static void main(String [] args){
        if (args.length == 0) {
            // if the user doesn't pass any command or option, add -h to show help
            args = new String[]{"-h"};
        }
        CommandLine cline = new CommandLine(new Csv2Phenopacket())
                .addSubcommand("download", new Csv2PhenopacketCommand())
                .addSubcommand("hbadeals", new IdCommand())
                .addSubcommand("interpro", new ParseCsvCommand());
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
