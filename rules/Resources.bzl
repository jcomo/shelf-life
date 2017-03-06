"""Generate filegroup rule for java resources. Expected to be under
the typical Maven project structure: src/main/resources or src/test/resources
"""

def Resources(is_test=False):
    if is_test:
        native.filegroup(
            name = "test_resources",
            srcs = native.glob(["src/test/resources/**/*"]),
            visibility = ["//" + PACKAGE_NAME + "/src/test:__subpackages__"],
        )
    else:
        native.filegroup(
            name = "resources",
            srcs = native.glob(["src/main/resources/**/*"]),
            visibility = ["//" + PACKAGE_NAME + "/src/main:__subpackages__"],
        )

