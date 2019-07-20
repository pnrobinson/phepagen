package org.monarchinitiative.csv2pp.cmd;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import org.monarchinitiative.phenol.io.OntologyLoader;
import org.monarchinitiative.phenol.ontology.data.Ontology;
import org.monarchinitiative.phenol.ontology.data.Term;
import org.monarchinitiative.phenol.ontology.data.TermId;

import java.io.*;

@Parameters(commandDescription = "translate HPO terms")
public class ParseCsvCommand extends Csv2PpCommand{
    @Parameter(names={"-h","--hpo"}, description = "path to hp.obo", required = true)
    private String hpopath;

    @Parameter(names={"-c","--csv"}, description = "path to csv file", required = true)
    private String csvpath;


    private Ontology ontology;

    private String [] header;

    public ParseCsvCommand(){}


    private void outputHpo(Writer writer, String field, int i) throws IOException {
        String [] termlist = field.split(",");
        for (String t : termlist) {
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
    public void run() {
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
    }
}
