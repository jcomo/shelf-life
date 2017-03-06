load("//rules:GenTestRules.bzl", "GenTestRules")

java_library(
  name = "stilltasty",
  srcs = glob(["**/*.java"]),
  deps = [
    "//stilltasty_client/src/main/java/me/jcomo/stilltasty",
    "//third_party:io_dropwizard_dropwizard_core",
    "//third_party:io_dropwizard_dropwizard_testing",
    "//third_party:junit_junit",
    "//third_party:net_javacrumbs_json_unit_json_unit_core",
    "//third_party:org_apache_httpcomponents_httpclient",
    "//third_party:org_assertj_assertj_core",
    "//third_party:org_mockito_mockito_core",
  ],
)

GenTestRules(
  name = "GeneratedTestRules",
  test_files = glob(["**/*Test.java"]),
  deps = [
    ":stilltasty",
  ],
  resources = [
    "//stilltasty_client:test_resources"
  ],
)
