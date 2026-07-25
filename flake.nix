{
  description = "Android Kotlin development environment (nixpkgs-only, no Android CLI)";

  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/nixos-unstable";
    flake-utils.url = "github:numtide/flake-utils";
  };

  outputs = {
    self,
    nixpkgs,
    flake-utils,
  }:
    flake-utils.lib.eachDefaultSystem (system: let
      pkgs = import nixpkgs {
        inherit system;
        config = {
          allowUnfree = true;
          android_sdk.accept_license = true;
        };
      };

      androidComposition = pkgs.androidenv.composeAndroidPackages {
        cmdLineToolsVersion = "11.0";
        toolsVersion = null;
        platformToolsVersion = "36.0.0";
        buildToolsVersions = ["35.0.0" "36.0.0"];
        platformVersions = ["36"];
        includeEmulator = true;
        emulatorVersion = "37.1.7";
        includeSystemImages = true;
        systemImageTypes = ["google_apis"];
        abiVersions = ["x86_64"];
        includeCmake = false;
        useGoogleAPIs = true;
      };

      androidSdk = androidComposition.androidsdk;
    in {
      devShells.default = pkgs.mkShell {
        buildInputs = [
          pkgs.android-studio
          androidSdk
          pkgs.jdk17
          pkgs.kotlin
          pkgs.gradle
        ];

        ANDROID_HOME = "${androidSdk}/libexec/android-sdk";
        ANDROID_SDK_ROOT = "${androidSdk}/libexec/android-sdk";
        JAVA_HOME = "${pkgs.jdk17}";

        shellHook = ''
          echo "Android Kotlin dev shell (nixpkgs androidenv, CLI-free)"
          echo "ANDROID_HOME=$ANDROID_HOME"
          echo "JAVA_HOME=$JAVA_HOME"
          echo "kotlin: $(kotlin -version 2>&1)"
          echo "gradle: $(gradle -version | grep Gradle)"
        '';
      };
    });
}
