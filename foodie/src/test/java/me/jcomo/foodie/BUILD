load("//rules:GenTestRules.bzl", "GenTestRules")

java_library(
  name = "foodie",
  srcs = glob(["**/*.java"]),
  deps = [
    "//foodie/src/main/java/me/jcomo/foodie",
    "//stilltasty-client/src/main/java/me/jcomo/stilltasty",
    "//:io_dropwizard_dropwizard_core",
    "//:io_dropwizard_dropwizard_testing",
    "//:junit_junit",
    "//:net_javacrumbs_json_unit_json_unit_fluent",
    "//:org_apache_httpcomponents_httpclient",
    "//:org_assertj_assertj_core",
    "//:org_mockito_mockito_core",
    "//:redis_clients_jedis",
  ],
)

GenTestRules(
  name = "GeneratedTestRules",
  test_files = glob(["**/*Test.java"]),
  deps = [
    ":foodie",
  ],
  resources = [
    "//foodie:test_resources",
  ],
)

