java_binary(
  name = "foodie",
  main_class = "me.jcomo.foodie.FoodieApplication",
  srcs = glob(["**/*.java"]),
  deps = [
    "//stilltasty-client/src/main/java/me/jcomo/stilltasty",
    "//:io_dropwizard_dropwizard_auth",
    "//:io_dropwizard_dropwizard_client",
    "//:io_dropwizard_dropwizard_core",
    "//:io_dropwizard_dropwizard_jdbi",
    "//:io_dropwizard_dropwizard_migrations",
    "//:org_mindrot_jbcrypt",
    "//:org_xerial_sqlite_jdbc",
    "//:redis_clients_jedis",
  ],
)
