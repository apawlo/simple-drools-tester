package com.github.apawlo.drools

import spock.lang.Specification

/**
 * Example tests
 */
class DroolsExecutorTest extends Specification {

    def executor = new DroolsExecutor()

    def "Should process multi-table ruleset"() {
        given:
        def context = [
                tableNames: ["IN_MIN_AGE_SUBS", "MIN_PREMIUM"],
                unitRole  : null,
                result    : null
        ]

        when:
        executor.run("/rules/DROOLS_EXAMPLE.xls", context)

        then:
        with(context) {
            result == 4
            MIN_PREMIUM == 40
        }
    }

    def "Should process multi-table ruleset with unitRole"() {
        given:
        def context = [
                tableNames: ["IN_MIN_AGE_SUBS", "MIN_PREMIUM"],
                unitRole  : "DIS",
                result    : null
        ]

        when:
        executor.run("/rules/DROOLS_EXAMPLE.xls", context)

        then:
        with(context) {
            result == 1
            MIN_PREMIUM == 10
        }
    }
}
