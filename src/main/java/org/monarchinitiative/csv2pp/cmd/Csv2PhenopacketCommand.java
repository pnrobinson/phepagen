package org.monarchinitiative.csv2pp.cmd;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import org.monarchinitiative.csv2pp.Csv2Phenopacket;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Parameters(commandDescription = "translate to Phenopacket")
public class Csv2PhenopacketCommand extends Csv2PpCommand{
    @Parameter(names={"--csv"}, description = "path to csv file", required = true)
    private String csvpath;



    public Csv2PhenopacketCommand(){
    }


    @Override
    public void run() {
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
    }
}
