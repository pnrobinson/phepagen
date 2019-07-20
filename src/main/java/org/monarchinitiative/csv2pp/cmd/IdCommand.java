package org.monarchinitiative.csv2pp.cmd;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import org.monarchinitiative.phenol.io.OntologyLoader;
import org.monarchinitiative.phenol.ontology.data.Ontology;
import org.monarchinitiative.phenol.ontology.data.Term;
import org.monarchinitiative.phenol.ontology.data.TermId;

import java.io.File;

@Parameters(commandDescription = "translate HPO terms")
public class IdCommand extends Csv2PpCommand {
    @Parameter(names={"-h","--hpo"}, description = "path to hp.obo", required = true)
    private String hpopath;
    @Parameter(names={"-t","--terms"}, description = "list of HPO term ids", required = true)
    private String terms;

    public  IdCommand(){}

    @Override
    public void run() {
        Ontology ontology = OntologyLoader.loadOntology(new File(hpopath));
        String [] termlist = terms.split(",");
        for (String t : termlist) {
            TermId tid = TermId.of(t);
            Term term = ontology.getTermMap().get(tid);
            System.out.println(term.getName() +"\t" + tid.getValue());
        }
    }
}
