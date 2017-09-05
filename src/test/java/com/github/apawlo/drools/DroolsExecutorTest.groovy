package com.github.apawlo.drools

import spock.lang.Specification
import spock.lang.Unroll

/**
 * Example tests
 */
class DroolsExecutorTest extends Specification {

    def executor = new DroolsExecutor()

    @Unroll
    def "Should process multi-table ruleset with unitRole = #unitRole"() {
        given:
        def context = [
                tableNames: ["IN_MIN_AGE_SUBS", "MIN_PREMIUM"],
                unitRole  : unitRole,
                result    : null
        ]

        when:
        executor.run("/rules/DROOLS_EXAMPLE.xls", context)

        then:
        with(context) {
            result == expectedResult
            MIN_PREMIUM == expectedMinPremium
        }

        where:
        unitRole || expectedResult | expectedMinPremium
        "DIS"    || 1.0            | 10.0
        "BO"     || 2.0            | 20.0
        "SU"     || 3              | 30
        "BOUND"  || 4              | 40
        null     || 4              | 40
    }
}
