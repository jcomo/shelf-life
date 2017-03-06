load("//rules:GenTestRules.bzl", "GenTestRules")

java_library(
  name = "stilltasty",
  srcs = glob(["**/*.java"]),
  deps = [
    "//stilltasty-client/src/main/java/me/jcomo/stilltasty",
    "//:io_dropwizard_dropwizard_testing",
    "//:io_dropwizard_dropwizard_core",
    "//:junit_junit",
    "//:net_javacrumbs_json_unit_json_unit_core",
    "//:org_apache_httpcomponents_httpclient",
    "//:org_assertj_assertj_core",
    "//:org_mockito_mockito_core",
  ],
)

GenTestRules(
  name = "GeneratedTestRules",
  test_files = glob(["**/*Test.java"]),
  deps = [
    ":stilltasty",
  ],
  resources = [
    "//stilltasty-client:test_resources"
  ],
)
