import abc


class AbstractManager(abc.ABC):

    @abc.abstractmethod
    def select(self):
        pass

    @abc.abstractmethod
    def where(self):
        pass

    @abc.abstractmethod
    def order_by(self):
        pass

    @abc.abstractmethod
    def limit(self):
        pass


class AbstractTranslator(abc.ABC):
    pass


class AbstractMigration(abc.ABC):
    pass


class AbstractDatabase(abc.ABC):
    pass
