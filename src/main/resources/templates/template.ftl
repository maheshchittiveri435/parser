Feature: ${featureName}

<#list scenarios as scenario>
  Scenario: ${scenario.name}
    Given the API endpoint is "${scenario.url}"
    When the "${scenario.method}" request is sent
    Then the response code should be "200"
    <#if scenario.text?exists>
     And the response should contain "${scenario.text}"
        </#if><#if scenario.text?exists><#else></#if>
</#list>
