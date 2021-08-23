package org.monarchinitiative.csv2pp.cmd;


import org.monarchinitiative.csv2pp.Csv2Phenopacket;
import picocli.CommandLine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "translate to phenopacket", aliases = {"D"},
        mixinStandardHelpOptions = true,
        description = "Download files for prositometry")
public class Csv2PhenopacketCommand implements Callable<Integer> {
    @CommandLine.Option(names={"--csv"}, description = "path to csv file", required = true)
    private String csvpath;



    public Csv2PhenopacketCommand(){
    }


    @Override
    public Integer call() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(csvpath));
            String line;
            while ((line=br.readLine())!= null) {
                String [] fields = line.split("\t");
                Csv2Phenopacket c2p = new Csv2Phenopacket();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return 0;
    }
}
