/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.ac.kntu.style;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;

import net.sourceforge.pmd.PMDConfiguration;
import net.sourceforge.pmd.PmdAnalysis;
import net.sourceforge.pmd.Report;
import net.sourceforge.pmd.RulePriority;
import net.sourceforge.pmd.RuleViolation;
import net.sourceforge.pmd.lang.LanguageRegistry;

/**
 * @author m.lotfi
 */
public class CheckPMDTest {

    @Test
    public void testPMD() {

        /*
         * Configuration
         */
        PMDConfiguration config = new PMDConfiguration();

        config.setDefaultLanguageVersion(LanguageRegistry.findLanguageByTerseName("java").getVersion("17"));
        config.addInputPath(Path.of("src/main/"));
        config.setMinimumPriority(RulePriority.LOW);
        config.addRuleSet("ir/ac/kntu/style/ruleset.xml");
        config.setReportFormat("text");
        config.setIgnoreIncrementalAnalysis(true);

        /*
         * Analysis
         */
        try (PmdAnalysis pmd = PmdAnalysis.create(config)) {
            Report report = pmd.performAnalysisAndCollectReport();

            List<RuleViolation> violations = report.getViolations();

            System.out.println("Found " + violations.size() + " PMD rule violations.");
            assertEquals(0, violations.size(), violations.size() + " PMD rule violations found.");
        }
    }
}
