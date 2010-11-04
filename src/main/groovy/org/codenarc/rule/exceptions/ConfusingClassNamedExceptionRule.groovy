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
package org.codenarc.rule.exceptions

import org.codenarc.rule.AbstractAstVisitor
import org.codenarc.rule.AbstractAstVisitorRule
import org.codehaus.groovy.ast.ClassNode
import org.codenarc.util.AstUtil

/**
 * This rule traps classes named exception that do not inherit from excecption.
 *
 * @author Hamlet D'Arcy
 * @version $Revision: 24 $ - $Date: 2009-01-31 13:47:09 +0100 (Sat, 31 Jan 2009) $
 */
class ConfusingClassNamedExceptionRule extends AbstractAstVisitorRule {
    String name = 'ConfusingClassNamedException'
    int priority = 2
    Class astVisitorClass = ConfusingClassNamedExceptionAstVisitor
}

class ConfusingClassNamedExceptionAstVisitor extends AbstractAstVisitor {

    def void visitClassEx(ClassNode node) {

        if (node.name.endsWith('Exception')) {
            if (!AstUtil.classNodeImplementsType(node, Exception)) {
                addViolation node, "Found a class named $node.name that does not extend Exception."
            }
        }
        super.visitClassEx(node)
    }

}
