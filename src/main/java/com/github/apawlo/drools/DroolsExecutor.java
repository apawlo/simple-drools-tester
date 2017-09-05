package com.github.apawlo.drools;

import org.drools.decisiontable.InputType;
import org.drools.decisiontable.SpreadsheetCompiler;
import org.kie.api.KieBase;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.utils.KieHelper;

import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

import static java.lang.String.format;

public class DroolsExecutor {

    public void run(String xlsResource, Map<String, Object> context) {
        KieSession knowledgeSession = getSession(xlsResource);
        try {
            knowledgeSession.insert(context);
            knowledgeSession.fireAllRules();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            knowledgeSession.dispose();
        }
    }

    private KieSession getSession(String xlsResource) {
        KieHelper kieHelper = new KieHelper();
        InputStream stream = Optional.ofNullable(getClass().getResourceAsStream(xlsResource))
                .orElseThrow(() -> new IllegalArgumentException(format("Resource '%s' does not exist", xlsResource)));
        Resource resource = ResourceFactory.newInputStreamResource(stream);

        SpreadsheetCompiler compiler = new SpreadsheetCompiler();
        String compiled = compiler.compile(resource, InputType.XLS);

        kieHelper.addContent(compiled, ResourceType.DRL);
        KieBase kieBase = kieHelper.build();
        return kieBase.newKieSession();
    }
}
