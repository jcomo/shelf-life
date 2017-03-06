java_library(
  name = "foodie",
  srcs = glob(["**/*.java"]),
  deps = [
    "//stilltasty_client/src/main/java/me/jcomo/stilltasty",
    "//third_party:io_dropwizard_dropwizard_auth",
    "//third_party:io_dropwizard_dropwizard_client",
    "//third_party:io_dropwizard_dropwizard_core",
    "//third_party:io_dropwizard_dropwizard_jdbi",
    "//third_party:io_dropwizard_dropwizard_migrations",
    "//third_party:org_mindrot_jbcrypt",
    "//third_party:org_xerial_sqlite_jdbc",
    "//third_party:redis_clients_jedis",
  ],
  visibility = ["//visibility:public"],
)

java_binary(
  name = "app",
  main_class = "me.jcomo.foodie.FoodieApplication",
  runtime_deps = [
    ":foodie",
  ],
)
