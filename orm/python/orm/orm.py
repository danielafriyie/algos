class ModelMeta(type):
    pass


class Model(metaclass=ModelMeta):

    def select(self):
        pass

    def where(self):
        pass

    def order_by(self):
        pass

    def limit(self):
        pass
