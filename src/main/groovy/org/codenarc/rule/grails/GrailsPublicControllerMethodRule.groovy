/*
 * Copyright 2009 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.codenarc.rule.grails

import org.codenarc.rule.AbstractAstVisitorRule
import org.codenarc.rule.AbstractAstVisitor
import org.codehaus.groovy.ast.MethodNode
import org.codenarc.util.WildcardPattern

/**
 * Rule that checks for public methods on Grails controller classes. Static methods are ignored.
 * <p/>
 * Grails controller actions and interceptors are defined as properties on the controller class.
 * Public methods on a controller class are unnecessary. They break encapsulation and can
 * be confusing.
 * <p/>
 * The <code>ignoreMethodNames</code> property optionally specifies one or more
 * (comma-separated) method names that should be ignored (i.e., that should not cause a
 * rule violation). The name(s) may optionally include wildcard characters ('*' or '?').
 * <p/>
 * This rule sets the default value of <code>applyToFilesMatching</code> to only match files
 * under the 'grails-app/controllers' folder. You can override this with a different regular
 * expression value if appropriate.
 * <p/>
 * This rule also sets the default value of <code>applyToClassNames</code> to only match class
 * names ending in 'Controller'. You can override this with a different class name pattern
 * (String) if appropriate.
 *
 * @author Chris Mair
 * @author Hamlet D'Arcy
 * @version $Revision$ - $Date$
 */
class GrailsPublicControllerMethodRule extends AbstractAstVisitorRule {
    String name = 'GrailsPublicControllerMethod'
    int priority = 2
    String ignoreMethodNames
    Class astVisitorClass = GrailsPublicControllerMethodAstVisitor
    String applyToFilesMatching = GrailsUtil.CONTROLLERS_FILES
    String applyToClassNames = GrailsUtil.CONTROLLERS_CLASSES
}

class GrailsPublicControllerMethodAstVisitor extends AbstractAstVisitor  {
    void visitMethodEx(MethodNode methodNode) {
        if ( (methodNode.modifiers & MethodNode.ACC_PUBLIC)
                && !(methodNode.modifiers & MethodNode.ACC_STATIC)
                && !isIgnoredMethodName(methodNode))  {
            addViolation(methodNode)
        }
        super.visitMethodEx(methodNode)
    }

    private boolean isIgnoredMethodName(MethodNode node) {
        return new WildcardPattern(rule.ignoreMethodNames, false).matches(node.name)
    }
}