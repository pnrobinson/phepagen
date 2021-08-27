package org.monarchinitiative.phenogen.cmd;


import com.google.protobuf.util.JsonFormat;
import org.monarchinitiative.phenogen.except.PhenogenRuntimeException;
import org.monarchinitiative.phenogen.phenopacket.*;
import org.phenopackets.schema.v2.Phenopacket;
import picocli.CommandLine;

import java.io.*;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "create", aliases = {"C"},
        mixinStandardHelpOptions = true,
        description = "generate phenopackets for testing")
public class PhenoGenCommand implements Callable<Integer> {
    @CommandLine.Option(names={"-o","--out"}, description = "output directory")
    private String outdir="data";


    public PhenoGenCommand(){}

    @Override
    public Integer call() {
        createOutdirectoryIfNeeded();
        outputRareDiseaseExamples();
        outputCancerExamples();
        return 0;
    }

    private void outputRareDiseaseExamples() {
        PhenopacketFactory mcahs1 = new RareDiseaseMCAHS1();
        outputPhenopacket("mcahs1.json", mcahs1.phenopacket());
        PhenopacketFactory thrombocytopenia2 = new RareDiseaseThrombocytopenia2();
        outputPhenopacket("thrombocytopenia2.json", thrombocytopenia2.phenopacket());
    }

    private void outputCancerExamples() {
        PhenopacketFactory aml = new CancerAML();
        outputPhenopacket("aml.json", aml.phenopacket());
        PhenopacketFactory scc = new CancerSquamousCell();
        outputPhenopacket("squamousCellCarcinoma.json", aml.phenopacket());
    }




    private void outputPhenopacket(String fname, Phenopacket phenopacket) {
        String path = this.outdir + File.separator + fname;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            String json =  JsonFormat.printer().print(phenopacket);
            writer.write(json);
        } catch (IOException e) {
            throw new PhenogenRuntimeException(e.getMessage());
        }
    }


    private void createOutdirectoryIfNeeded() {
        File directory = new File(this.outdir);
        if (! directory.exists()){
            boolean success = directory.mkdir();
            if (!success) {
                throw new PhenogenRuntimeException("Could not create outdir");
            }
        }
    }
}
