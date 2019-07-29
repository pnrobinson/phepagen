package org.monarchinitiative.csv2pp;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import org.monarchinitiative.csv2pp.cmd.Csv2PhenopacketCommand;
import org.monarchinitiative.csv2pp.cmd.Csv2PpCommand;
import org.monarchinitiative.csv2pp.cmd.IdCommand;
import org.monarchinitiative.csv2pp.cmd.ParseCsvCommand;

public class Csv2Phenopacket {
    @Parameter(names = {"-h", "--help"}, help = true, arity = 0,description = "display this help message")
    private boolean usageHelpRequested;

    public static void main(String [] args){
        Csv2Phenopacket csv = new Csv2Phenopacket();
        IdCommand id = new IdCommand();
        ParseCsvCommand pcsv = new ParseCsvCommand();
        Csv2PhenopacketCommand c2p = new Csv2PhenopacketCommand();

        JCommander jc = JCommander.newBuilder()
                .addObject(csv)
                .addCommand("id", id)
                .addCommand("csv",pcsv)
                .addCommand("c2p",c2p)
                .build();
        jc.setProgramName("java -jar csv2pp.jar");

        try {
            jc.parse(args);
        } catch (ParameterException e) {
            e.printStackTrace();
        }
        String command = jc.getParsedCommand();
        Csv2PpCommand csvcommand=null;
        switch (command) {
            case "id":
                csvcommand = id;
                break;
            case "csv":
                csvcommand = pcsv;
                break;
            case "c2p":
                csvcommand = c2p;
                break;
        }
        csvcommand.run();
    }


    public Csv2Phenopacket() {
    }
}
