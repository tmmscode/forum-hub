# Usa a imagem com JDK 21 (versão específica recomendada)
FROM eclipse-temurin:21.0.2_13-jdk

# Define o diretório de trabalho
WORKDIR /app

# Copia o jar gerado para dentro do container
COPY target/forumhub.jar app.jar

# Expõe a porta que a aplicação usa (ajuste se necessário)
EXPOSE 8080

# Executa a aplicação
CMD ["java", "-jar", "app.jar"]
