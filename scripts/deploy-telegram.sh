#!/usr/bin/env sh

CHANGELOG="$(./scripts/changelog.sh)"

rm -f app/build/outputs/apk/travis/output.json
APK_NAME_OCTO=$(find -1 app/build/outputs/apk/travis/ | tr -d '\n')

#curl -F chat_id="-1001357118452" -F sticker="CAADBAAD8R8AAmSKPgABCOk3mg-Zvf0C" https://api.telegram.org/bot${BOT_TOKEN}/sendSticker
curl -F chat_id="-1001357118452" -F document=@"app/build/outputs/apk/travis/${APK_NAME_OCTO}" https://api.telegram.org/bot"${BOT_TOKEN}"/sendDocument
curl -F chat_id="-1001357118452" -F text="${CHANGELOG}" -F parse_mode="HTML" -F disable_web_page_preview="true" https://api.telegram.org/bot"${BOT_TOKEN}"/sendMessage

./scripts/changelog.sh