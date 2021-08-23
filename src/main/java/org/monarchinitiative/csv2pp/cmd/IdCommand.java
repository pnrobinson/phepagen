package org.monarchinitiative.csv2pp.cmd;


import org.monarchinitiative.phenol.io.OntologyLoader;
import org.monarchinitiative.phenol.ontology.data.Ontology;
import org.monarchinitiative.phenol.ontology.data.Term;
import org.monarchinitiative.phenol.ontology.data.TermId;
import picocli.CommandLine;

import java.io.File;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "translate to phenopacket", aliases = {"I"},
        mixinStandardHelpOptions = true,
        description = "Download files for prositometry")
public class IdCommand implements Callable<Integer> {
    @CommandLine.Option(names={"-h","--hpo"}, description = "path to hp.obo", required = true)
    private String hpopath;
    @CommandLine.Option(names={"-t","--terms"}, description = "list of HPO term ids", required = true)
    private String terms;

    public  IdCommand(){}

    @Override
    public Integer call() {
        Ontology ontology = OntologyLoader.loadOntology(new File(hpopath));
        String [] termlist = terms.split(",");
        for (String t : termlist) {
            TermId tid = TermId.of(t);
            Term term = ontology.getTermMap().get(tid);
            System.out.println(term.getName() +"\t" + tid.getValue());
        }
        return 0;
    }
}
