.PHONY: build
build:
		@echo "#---------------------------#"
		@echo "# Buildを行います"
		@echo "#---------------------------#"
		gradle build
.PHONY: dependencies
dependencies:
		@echo "#---------------------------#"
		@echo "# 依存関係を解決します"
		@echo "#---------------------------#"
		gradle dependencies
.PHONY: run
run:
		@echo "#---------------------------#"
		@echo "# 実行します"
		@echo "#---------------------------#"
		gradle run
.PHONY: clean
clean:
		@echo "#---------------------------#"
		@echo "# クリーンを行います"
		@echo "#---------------------------#"
		gradle clean