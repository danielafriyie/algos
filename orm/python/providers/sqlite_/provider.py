from ..base import (
    AbstractManager,
    AbstractTranslator,
    AbstractMigration,
    AbstractDatabase
)


class Translator(AbstractTranslator):
    pass


class Manager(AbstractManager):
    pass


class Migration(AbstractMigration):
    pass


class Database(AbstractDatabase):
    pass
