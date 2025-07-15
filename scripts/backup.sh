#!/usr/bin/env bash

# This script back-ups postgres container using pg_dump

log() {
  echo "$(date +%FT%TZ) :: $1" | tee -a "$BACKUP_LOG"
}

DB_NAME=horizon
DB_USER=horizon_app
DOCKER_POSTGRES_NAME="horizon_postgres"
CLOUD_BACKUP_DIR="dropbox_enc:$DOCKER_POSTGRES_NAME/"
BACKUP_LOCATION="$HOME/backup/$DOCKER_POSTGRES_NAME"
BACKUP_FILE="$BACKUP_LOCATION/backup_$(date +%Y%m%d"-"%H%M%S).sql"
BACKUP_LOG="$BACKUP_LOCATION/runs.log"

log "Starting $DOCKER_POSTGRES_NAME backup"

#log "Logging full docker ps"
#docker ps | tee -a "$BACKUP_LOG"

log "Getting container ID"
CONTAINER_ID=$(docker ps -q --filter name=$DOCKER_POSTGRES_NAME)
log "Container ID is $CONTAINER_ID"

if [ ! -d "$BACKUP_LOCATION" ]; then
  log "Creating backup location"
  mkdir -p "$BACKUP_LOCATION"
fi

log "Backup up database $DB_NAME"
docker exec -i "$CONTAINER_ID" pg_dump -U $DB_USER $DB_NAME > "$BACKUP_FILE"

log "Copying to remote $CLOUD_BACKUP_DIR"
rclone copy "$BACKUP_FILE" "$CLOUD_BACKUP_DIR"

log "Removing old backups from $CLOUD_BACKUP_DIR, keeping 2-month daily backup"
rclone delete $CLOUD_BACKUP_DIR --min-age 60d
rclone delete $CLOUD_BACKUP_DIR --min-age 7d --exclude "*00000*"

log "Backup complete"
