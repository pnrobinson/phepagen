package org.monarchinitiative.csv2pp.cmd;


import org.monarchinitiative.phenol.io.OntologyLoader;
import org.monarchinitiative.phenol.ontology.data.Ontology;
import org.monarchinitiative.phenol.ontology.data.Term;
import org.monarchinitiative.phenol.ontology.data.TermId;
import picocli.CommandLine;

import java.io.*;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "translate to phenopacket", aliases = {"P"},
        mixinStandardHelpOptions = true,
        description = "Download files for prositometry")
public class ParseCsvCommand implements Callable<Integer> {
    @CommandLine.Option(names={"-h","--hpo"}, description = "path to hp.obo", required = true)
    private String hpopath;

    @CommandLine.Option(names={"-c","--csv"}, description = "path to csv file", required = true)
    private String csvpath;


    private Ontology ontology;

    private String [] header;

    public ParseCsvCommand(){}


    private void outputHpo(Writer writer, String field, int i) throws IOException {
        String [] termlist = field.split("[,\\s]");
        for (String t : termlist) {
            if (t.isEmpty()) continue;
            System.out.printf("%s\n",t);
            TermId tid = TermId.of(t.trim());
            Term term = ontology.getTermMap().get(tid);
            if (term == null) {
                writer.write(header[i]+": Could not identify term for \"" + t +"\"");
            } else {
                writer.write(header[i]+": "+term.getName() + "\t" + tid.getValue());
            }
        }
    }


    private void outputParsed(Writer writer, String line) throws IOException {
        String []A = line.split("\t");
        if (A.length < 5) return;
        int i = 0;
        for (String a : A) {
            if (a.isEmpty()) { i++; continue; }
            if (a.contains("HP:")) {
                outputHpo(writer,a, i);
            } else {
                writer.write(header[i] + ": " +a + "\n");
            }
            i++;
        }
    }




    @Override
    public Integer call() {
        ontology = OntologyLoader.loadOntology(new File(hpopath));
        try {
            BufferedReader br = new BufferedReader(new FileReader(csvpath));
            BufferedWriter bw = new BufferedWriter(new FileWriter("myoutfile.txt"));
            String line;
            line = br.readLine();
            header = line.split("\t");
            while ((line=br.readLine())!=null) {
                outputParsed(bw,line);
                bw.write("\n\n***********************\n\n");
            }
            br.close();
            bw.close();
        } catch (IOException e){
            e.printStackTrace();
        }
        return 0;
    }
}
