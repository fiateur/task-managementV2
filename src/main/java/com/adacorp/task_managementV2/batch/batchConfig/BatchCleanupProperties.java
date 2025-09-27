package com.adacorp.task_managementV2.batch.batchConfig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * FDE_77421 : Dernière Connexion
 * Classe de configuration pour lire ces propriétés
 * Implémentation du Process de Mise à Jour d'inactivité Utilisateur après XX Jours de Non-Connexion Utilisateur.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "saga.batch.cleanup")
public class BatchCleanupProperties {

    private String cron;
    private int inactiveDays;
    private int fixedRate;
}

